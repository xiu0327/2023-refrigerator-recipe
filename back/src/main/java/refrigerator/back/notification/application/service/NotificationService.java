package refrigerator.back.notification.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ModifyNotificationUseCase;
import refrigerator.back.notification.application.port.out.ReadNotification;
import refrigerator.back.notification.application.port.out.WriteNotification;

import java.util.List;

@Service
public class NotificationService implements FindNotificationListUseCase, ModifyNotificationUseCase {

    ReadNotification readNotification;
    WriteNotification writeNotification;

    // 알림 조회
    @Override
    public List<NotificationResponseDTO> getNotificationList(String email, int page, int size) {

        MemberNotification memberNotification = readNotification.getMemberNotification(email);
        memberNotification.signOff();
        writeNotification.saveMemberNotification(memberNotification);

        return readNotification.getNotificationList(email, page, size);
    }

    // 신규 알림 생성 조회
    @Override
    public boolean getNotificationSign(String email) {
        return readNotification.getMemberNotification(email).isSign();
    }

    // 알림 수정 (안읽음 -> 읽음)
    @Override
    public void modifyNotification(Long id) {

        Notifications notification = readNotification.getNotification(id);
        notification.isRead();
        writeNotification.saveNotification(notification);
    }

    // 댓글 좋아요 알림 생성
    // - 댓글 좋아요 버튼 로직에 있어야함. => comment
    public void createNotification(String email) {

        /// ... 댓글 좋아요에 관련된 내용
        // Notification 생성에관련된 내용

        // 알림 생성
        writeNotification.saveNotification(
                Notifications.create("test.png", "!!!!", "a", "wdqwd", "get", "dwdq")
        );

        // Sign 변경
        MemberNotification memberNotification = readNotification.getMemberNotification(email);
        memberNotification.signOn();
        writeNotification.saveMemberNotification(memberNotification);
    }
}