package refrigerator.back.notification.application.port.batch;

import java.time.LocalDateTime;

public interface DeleteNotificationBatchPort {
    void deleteNotification(LocalDateTime date);

    void deleteDeadlineNotification(LocalDateTime date);

    void deleteTestNotification(LocalDateTime date);

}
