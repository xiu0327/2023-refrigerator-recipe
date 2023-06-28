package refrigerator.back.notification.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.adapter.repository.NotificationPersistenceRepository;
import refrigerator.back.notification.adapter.repository.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.out.notification.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.notification.FindNotificationPort;
import refrigerator.back.notification.application.port.out.notification.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.notification.UpdateNotificationReadStatusPort;

import java.util.List;

import static refrigerator.back.notification.exception.NotificationExceptionType.NOTIFICATION_READ_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements UpdateNotificationReadStatusPort, SaveNotificationPort, FindNotificationListPort, FindNotificationPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final NotificationPersistenceRepository notificationPersistenceRepository;

    @Override
    public List<Notification> findNotificationList(String email, int page, int size) {
        return notificationQueryRepository.findNotificationList(email, PageRequest.of(page, size));
    }

    @Override
    public Notification findNotification(Long id) {
        return notificationPersistenceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOTIFICATION_READ_FAIL));
    }

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        if (notificationQueryRepository.updateReadStatus(notificationId, status) != 1)
            throw new BusinessException(NOTIFICATION_READ_FAIL);
    }

    @Override
    public Long saveNotification(Notification notification) {
        return notificationPersistenceRepository.save(notification).getId();
    }
}
