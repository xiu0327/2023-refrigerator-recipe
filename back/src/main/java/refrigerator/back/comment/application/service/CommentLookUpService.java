package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.adapter.mapper.CommentMapper;
import refrigerator.back.comment.application.domain.CommentDto;
import refrigerator.back.comment.application.domain.CommentListDto;
import refrigerator.back.comment.application.port.in.comment.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindCommentPreviewListUseCase;
import refrigerator.back.comment.application.port.in.comment.FindMyCommentsUseCase;
import refrigerator.back.comment.application.port.out.CommentReadPort;
import refrigerator.back.comment.application.port.out.FindMyCommentListPort;

import java.util.ArrayList;
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
    public InCommentListDTO findCommentPreviews(Long recipeId, String memberId, int size) {
        CommentListDto commentListDto = commentReadPort.findCommentPreviewList(recipeId, size);
        return InCommentListDTO.builder()
                        .comments(commentListDto.getComments().stream()
                                .map(dto -> mapping(dto, checkMyComment(dto.getMemberId(), memberId)))
                                .collect(Collectors.toList()))
                        .count(commentListDto.getCount()).build();
    }

    @Override
    public List<InCommentDTO> findCommentsByHeart(Long recipeId, String memberId, int page, int size) {
        List<CommentDto> comments = commentReadPort.findCommentListByHeart(recipeId, memberId, page, size);
        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<InCommentDTO> findCommentsByDate(Long recipeId, String memberId, int page, int size) {
        List<CommentDto> comments = commentReadPort.findCommentListByDate(recipeId, memberId, page, size);
        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }

    private InCommentDTO mapping(CommentDto comment, Boolean isMyComment){
        return mapper.toInCommentDto(comment,
                commentTimeService.replace(comment.getCreateDate()),
                isMyComment);
    }

    private Boolean checkMyComment(String commentMemberId, String loginMemberId) {
        return commentMemberId.equals(loginMemberId);
    }

    @Override
    public List<InCommentDTO> findMyComments(String memberId) {
        List<CommentDto> comments = findMyCommentListPort.findMyComments(memberId);
        return comments.stream()
                .map(commentDto -> mapping(commentDto, checkMyComment(commentDto.getMemberId(), memberId)))
                .collect(Collectors.toList());
    }
}
