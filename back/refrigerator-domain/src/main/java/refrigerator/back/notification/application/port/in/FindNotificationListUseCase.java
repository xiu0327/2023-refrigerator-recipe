package refrigerator.back.notification.application.port.in;


import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;

import java.util.List;

public interface FindNotificationListUseCase {
    List<NotificationResponseDTO> getNotificationList(String email, int page, int size);
}
