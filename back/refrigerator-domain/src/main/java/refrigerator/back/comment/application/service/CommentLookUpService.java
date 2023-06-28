package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.dto.CommentDTO;
import refrigerator.back.comment.application.dto.InCommentDTO;
import refrigerator.back.comment.application.dto.CommentListAndCountDTO;
import refrigerator.back.comment.application.mapper.CommentMapper;
import refrigerator.back.comment.application.port.in.comment.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindCommentPreviewListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindMyCommentsUseCase;
import refrigerator.back.comment.application.port.out.trash.CommentReadPort;
import refrigerator.back.comment.application.port.out.trash.FindMyCommentListPort;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLookUpService implements FindCommentListUseCase, FindCommentPreviewListUseCase, FindMyCommentsUseCase {

    private final CommentReadPort commentReadPort;
    private final FindMyCommentListPort findMyCommentListPort;
    private final CommentTimeService commentTimeService;
    private final CommentMapper mapper;

    @Override
    public CommentListAndCountDTO<InCommentDTO> findCommentPreviews(Long recipeId, String memberId, int size) {

        CommentListAndCountDTO<CommentDTO> dtoList = commentReadPort.findCommentPreviewList(recipeId, size);

        List<InCommentDTO> comments = dtoList.getComments()
                .stream()
                .map(dto -> mapping(dto, checkMyComment(dto.getMemberId(), memberId)))
                .collect(Collectors.toList());

        Integer count = dtoList.getCount();

        return CommentListAndCountDTO.<InCommentDTO>builder()
                        .comments(comments)
                        .count(count).build();
    }

    @Override
    public List<InCommentDTO> findCommentsByHeart(Long recipeId, String memberId, int page, int size) {

        List<CommentDTO> comments = commentReadPort.findCommentListByHeart(recipeId, memberId, page, size);

        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<InCommentDTO> findCommentsByDate(Long recipeId, String memberId, int page, int size) {

        List<CommentDTO> comments = commentReadPort.findCommentListByDate(recipeId, memberId, page, size);

        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<InCommentDTO> findMyComments(String memberId, Long recipeId) {

        List<CommentDTO> comments = findMyCommentListPort.findMyComments(memberId, recipeId);

        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }

    private InCommentDTO mapping(CommentDTO comment, Boolean isMyComment){

        return mapper.toInCommentDto(comment,
                commentTimeService.replace(comment.getCreateDate()),
                isMyComment);
    }

    private Boolean checkMyComment(String commentMemberId, String loginMemberId) {
        return commentMemberId.equals(loginMemberId);
    }
}
