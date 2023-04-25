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
import refrigerator.back.comment.application.port.out.CommentReadPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLookUpService implements FindCommentListUseCase, FindCommentPreviewListUseCase {

    private final CommentReadPort commentReadPort;
    private final CommentTimeService commentTimeService;
    private final CommentMapper mapper;

    @Override
    public InCommentListDTO findCommentPreviews(Long recipeId, int size) {
        CommentListDto commentListDto = commentReadPort.findCommentPreviewList(recipeId, size);
        return InCommentListDTO.builder()
                        .comments(mapping(commentListDto.getComments()))
                        .count(commentListDto.getCount()).build();
    }

    @Override
    public List<InCommentDTO> findCommentsByHeart(Long recipeId, int page, int size) {
        List<CommentDto> comments = commentReadPort.findCommentListByHeart(recipeId, page, size);
        return mapping(comments);
    }

    @Override
    public List<InCommentDTO> findCommentsByDate(Long recipeId, int page, int size) {
        List<CommentDto> comments = commentReadPort.findCommentListByDate(recipeId, page, size);
        return mapping(comments);
    }

    private List<InCommentDTO> mapping(List<CommentDto> comments){
        return comments.stream()
                .map(dto -> mapper.toInCommentDto(dto, commentTimeService.replace(dto.getCreateDate())))
                .collect(Collectors.toList());
    }
}
