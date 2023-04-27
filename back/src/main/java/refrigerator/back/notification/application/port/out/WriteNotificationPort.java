package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;

public interface WriteNotificationPort {

    // 멤버 알림 저장 (수정, 생성)
    Long saveMemberNotification(MemberNotification memberNotification);

    // 알림 저장 (수정, 생성)
    Long saveNotification(Notifications notification);
}
