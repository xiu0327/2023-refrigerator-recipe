package refrigerator.back.notification.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.adapter.out.repository.NotificationRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.out.write.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.write.UpdateNotificationReadStatusPort;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements UpdateNotificationReadStatusPort, SaveNotificationPort {

    private final NotificationRepository notificationRepository;

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        notificationRepository.updateReadStatus(notificationId, status);
    }

    @Override
    public Long saveNotification(Notification notification) {
        return notificationRepository.save(notification).getId();
    }
}
