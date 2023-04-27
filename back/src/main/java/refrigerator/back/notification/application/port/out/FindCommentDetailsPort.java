package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.CommentNotificationDetails;

public interface FindCommentDetailsPort {
    CommentNotificationDetails getDetails(Long commentId);
}
