package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.Notification;

import java.util.List;

public interface ReadNotificationPort {
    List<Notification> getNotificationList(String email, int page, int size);
    Notification getNotification(Long id);
}
