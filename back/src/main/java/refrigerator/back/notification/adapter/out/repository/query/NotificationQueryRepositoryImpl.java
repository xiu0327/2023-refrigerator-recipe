package refrigerator.back.notification.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static refrigerator.back.notification.application.domain.QNotification.*;


@Repository
@RequiredArgsConstructor
public class NotificationQueryRepositoryImpl implements NotificationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        jpaQueryFactory.update(notification)
                .set(notification.readStatus, status)
                .where(notification.id.eq(notificationId))
                .execute();
        em.flush();
        em.clear();
    }

}
