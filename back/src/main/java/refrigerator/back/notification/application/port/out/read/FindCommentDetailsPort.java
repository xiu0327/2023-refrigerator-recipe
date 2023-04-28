package refrigerator.back.notification.application.port.out.read;

import refrigerator.back.notification.application.domain.CommentNotificationDetails;

public interface FindCommentDetailsPort {
    CommentNotificationDetails getDetails(Long commentId);
}
