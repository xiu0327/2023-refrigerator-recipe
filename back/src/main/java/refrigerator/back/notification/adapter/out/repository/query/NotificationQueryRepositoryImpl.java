package refrigerator.back.notification.adapter.out.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.application.domain.MemberNotification;

import javax.persistence.EntityManager;


@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

    private final EntityManager em;

    @Override
    public Long saveMemberNotification(MemberNotification notification) {
        em.persist(notification);
        return notification.getId();
    }
}
