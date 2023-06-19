package refrigerator.back.comment.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.repository.CommentRepository;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.out.CreateCommentHeartPort;
import refrigerator.back.comment.application.port.out.DeleteCommentHeartPort;
import refrigerator.back.comment.application.port.out.UpdateCommentHeartPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;


@Repository
@RequiredArgsConstructor
public class CommentHeartAdapter implements
        UpdateCommentHeartPort,
        CreateCommentHeartPort,
        DeleteCommentHeartPort {

    private final CommentRepository repository;

    @Override
    @Transactional
    public void add(Long commentId) {
        long result = repository.updateCommentHeartCount(commentId, 1);
        if (result == 0){
            throw new BusinessException(CommentExceptionType.DELETE_COMMENT);
        }
    }

    @Override
    @Transactional
    public void reduce(Long commentId) {
        long result = repository.updateCommentHeartCount(commentId, -1);
        if (result == 0){
            throw new BusinessException(CommentExceptionType.DELETE_COMMENT);
        }
    }

    @Override
    @Transactional
    public Long create(CommentHeart commentHeart) {
        repository.persistCommentHeart(commentHeart);
        return commentHeart.getCommentId();
    }

    @Override
    public void delete(Long commentId) {
        long result = repository.deleteCommentHeartCount(commentId);
        if (result == 0){
            throw new BusinessException(CommentExceptionType.DELETE_COMMENT);
        }
    }
}
