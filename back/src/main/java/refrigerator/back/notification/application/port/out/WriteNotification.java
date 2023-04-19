package refrigerator.back.notification.application.port.out;

import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;

public interface WriteNotification {

    // 멤버 알림 저장 (수정, 생성)
    void saveMemberNotification(MemberNotification memberNotification);

    // 알림 저장 (수정, 생성)
    void saveNotification(Notifications notification);
}
