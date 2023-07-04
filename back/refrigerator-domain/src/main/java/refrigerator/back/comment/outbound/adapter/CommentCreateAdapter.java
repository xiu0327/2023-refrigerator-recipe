package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.out.CreateCommentPort;

@Repository
@RequiredArgsConstructor
public class CommentCreateAdapter implements CreateCommentPort {

    private final CommentJpaRepository commentRepository;
    private final CommentHeartJpaRepository commentHeartRepository;

    @Override
    public Long create(Comment comment) {
        commentRepository.save(comment);
        CommentHeart commentHeart = comment.createCommentHeart();
        commentHeartRepository.save(commentHeart);
        return comment.getCommentId();
    }
}
