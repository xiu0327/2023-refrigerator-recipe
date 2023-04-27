package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;

import java.util.List;

public interface ReadNotificationPort {

    // 알림 리스트 조회
    List<NotificationResponseDTO> getNotificationList(String email, int page, int size);

    // 신규 알림 생성 조회
    MemberNotification getMemberNotification(String email);

    // 알림 단건 조회
    Notifications getNotification(Long id);
}
