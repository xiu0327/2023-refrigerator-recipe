package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.CommentHeartValue;

public interface ChangeCommentHeartCountPort {
    Long change(Long commentId, CommentHeartValue value);
}
