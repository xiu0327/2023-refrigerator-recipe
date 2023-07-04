package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.Comment;

public interface CreateCommentPort {
    Long create(Comment comment);
}
