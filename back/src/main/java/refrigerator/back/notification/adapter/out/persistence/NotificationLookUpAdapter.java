package refrigerator.back.notification.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.adapter.out.repository.NotificationRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.out.read.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.read.FindNotificationPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationLookUpAdapter implements FindNotificationListPort, FindNotificationPort {

    private final NotificationRepository repository;

    @Override
    public List<Notification> findNotificationList(String email, int page, int size) {
        return repository.findNotificationList(email, PageRequest.of(page, size));
    }

    @Override
    public Notification getNotification(Long id) {
        return repository.findById(id).orElse(null);
    }

}
