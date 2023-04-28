package refrigerator.back.notification.application.port.out.read;

import refrigerator.back.notification.application.domain.Notification;

public interface FindNotificationPort {
    Notification getNotification(Long id);
}
