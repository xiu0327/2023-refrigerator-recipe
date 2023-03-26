package refrigerator.back.comment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.out.CommentHeartCreatePort;
import refrigerator.back.comment.application.port.out.CommentHeartUpdatePort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentHeartPersistenceAdapter implements CommentHeartUpdatePort, CommentHeartCreatePort {

    private final CommentRepository repository;

    @Override
    @Transactional
    public void add(Long commentId) {
        repository.updateCommentHeartCount(commentId, 1);
    }

    @Override
    @Transactional
    public void reduce(Long commentId) {
        repository.updateCommentHeartCount(commentId, -1);
    }

    @Override
    @Transactional
    public Long create(CommentHeart commentHeart) {
        repository.persistCommentHeart(commentHeart);
        return commentHeart.getCommentId();
    }
}
