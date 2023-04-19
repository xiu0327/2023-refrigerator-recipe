package refrigerator.back.notification.application.port.in;

import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;

import java.util.List;

public interface FindNotificationListUseCase {

    // 알림 조회
    List<NotificationResponseDTO> getNotificationList(String email, int page, int size);

    // 신규 알림 생성 조회
    boolean getNotificationSign(String email);

}
