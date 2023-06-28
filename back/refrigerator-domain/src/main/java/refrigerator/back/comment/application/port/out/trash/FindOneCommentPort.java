package refrigerator.back.comment.application.port.out.trash;



import refrigerator.back.comment.application.domain.Comment;

import java.util.Optional;

public interface FindOneCommentPort {
    Optional<Comment> findCommentById(Long commentId);
}
