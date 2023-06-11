package refrigerator.back.notification.application.port.out.read;


import refrigerator.back.notification.application.domain.Notification;

import java.util.List;

public interface FindNotificationListPort {
    List<Notification> findNotificationList(String email, int page, int size);
}
