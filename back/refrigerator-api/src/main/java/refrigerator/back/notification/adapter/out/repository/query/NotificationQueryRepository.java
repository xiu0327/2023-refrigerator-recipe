package refrigerator.back.notification.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;
import refrigerator.back.notification.application.domain.Notification;

import java.util.List;

public interface NotificationQueryRepository {
    void updateReadStatus(Long notificationId, boolean status);
    List<Notification> findNotificationList(String memberId, Pageable pageable);
    Integer countingNotReadNotification(String memberId);
}
