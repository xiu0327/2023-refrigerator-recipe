package refrigerator.back.comment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.mapper.CommentMapper;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentDto;
import refrigerator.back.comment.application.domain.CommentListDto;
import refrigerator.back.comment.application.port.out.CommentReadPort;
import refrigerator.back.comment.application.port.out.FindMyCommentListPort;
import refrigerator.back.comment.application.port.out.FindOneCommentPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static refrigerator.back.comment.adapter.out.persistence.CommentSortCondition.DATE;
import static refrigerator.back.comment.adapter.out.persistence.CommentSortCondition.HEART;

@Repository
@RequiredArgsConstructor
public class CommentLookUpAdapter implements CommentReadPort, FindOneCommentPort, FindMyCommentListPort {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public CommentListDto findCommentPreviewList(Long recipeId, int size) {
        Page<OutCommentDTO> result = repository.findCommentPreviewList(recipeId, PageRequest.of(0, size));
        return CommentListDto.builder()
                .comments(mapping(result.getContent()))
                .count(Long.valueOf(result.getTotalElements()).intValue())
                .build();
    }

    @Override
    public List<CommentDto> findCommentListByHeart(Long recipeId, String memberId, int page, int size) {
        List<OutCommentDTO> result = repository.findCommentList(recipeId, memberId, PageRequest.of(page, size), HEART);
        return mapping(result);
    }

    @Override
    public List<CommentDto> findCommentListByDate(Long recipeId, String memberId, int page, int size) {
        List<OutCommentDTO> result = repository.findCommentList(recipeId, memberId, PageRequest.of(page, size), DATE);
        return mapping(result);
    }

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return repository.findByCommentID(commentId);
    }


    @Override
    public List<CommentDto> findMyComments(String memberId) {
        List<OutCommentDTO> result = repository.findMyCommentList(memberId);
        return mapping(result);
    }

    private List<CommentDto> mapping(List<OutCommentDTO> result) {
        return result.stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

}
