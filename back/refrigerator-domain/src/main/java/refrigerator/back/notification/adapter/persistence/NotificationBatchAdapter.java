package refrigerator.back.notification.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.adapter.repository.NotificationBatchQueryRepository;
import refrigerator.back.notification.application.port.batch.DeleteNotificationBatchPort;

import java.time.LocalDateTime;

import static refrigerator.back.notification.application.domain.QNotification.notification;

@Component
@RequiredArgsConstructor
public class NotificationBatchAdapter implements DeleteNotificationBatchPort {

    private final NotificationBatchQueryRepository notificationBatchQueryRepository;

    public void deleteNotification(LocalDateTime date) {
        notificationBatchQueryRepository.deleteNotification(date);
    }

    public void deleteDeadlineNotification(LocalDateTime date) {
        notificationBatchQueryRepository.deleteDeadlineNotification(date);
    }

    public void deleteTestNotification(LocalDateTime date) {
        notificationBatchQueryRepository.deleteTestNotification(date);
    }
}
