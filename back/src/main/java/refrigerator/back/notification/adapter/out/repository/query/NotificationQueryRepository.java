package refrigerator.back.notification.adapter.out.repository.query;

import refrigerator.back.notification.application.domain.MemberNotification;

public interface NotificationQueryRepository {

    void saveMemberNotification(MemberNotification notification);
}
