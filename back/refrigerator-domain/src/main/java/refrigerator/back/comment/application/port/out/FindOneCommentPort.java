package refrigerator.back.comment.application.port.out;



import refrigerator.back.comment.application.domain.Comment;

import java.util.Optional;

public interface FindOneCommentPort {
    Optional<Comment> findCommentById(Long commentId);
}
