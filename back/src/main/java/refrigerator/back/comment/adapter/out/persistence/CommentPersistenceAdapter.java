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
import refrigerator.back.comment.application.port.out.CommentFindOnePort;
import refrigerator.back.comment.application.port.out.CommentReadPort;
import refrigerator.back.comment.application.port.out.CommentWritePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static refrigerator.back.comment.adapter.out.persistence.CommentSortCondition.*;

@Repository
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentReadPort, CommentWritePort, CommentFindOnePort {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public CommentListDto findCommentPreviewList(Long recipeId, int size) {
        Page<OutCommentDTO> result = repository.findCommentPreviewList(recipeId, PageRequest.of(0, size));
        return CommentListDto.builder()
                .comments(result.getContent()
                        .stream().map(mapper::toCommentDto)
                        .collect(Collectors.toList()))
                .count(Long.valueOf(result.getTotalElements()).intValue())
                .build();
    }

    @Override
    public List<CommentDto> findCommentListByHeart(Long recipeId, int page, int size) {
        return repository.findCommentList(recipeId, PageRequest.of(page, size), HEART)
                .stream().map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findCommentListByDate(Long recipeId, int page, int size) {
        return repository.findCommentList(recipeId, PageRequest.of(page, size), DATE)
                .stream().map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return repository.findByCommentID(commentId);
    }

    @Override
    public Optional<Comment> findCommentByRecipeId(Long recipeId) {
        return repository.findByRecipeID(recipeId);
    }

    @Override
    public Long persist(Comment comment) {
        repository.save(comment);
        return comment.getCommentID();
    }
}
