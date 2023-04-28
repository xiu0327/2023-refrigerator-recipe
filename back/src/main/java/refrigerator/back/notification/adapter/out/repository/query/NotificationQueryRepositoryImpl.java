package refrigerator.back.notification.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.application.domain.Notification;

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

    @Override
    public List<Notification> findNotificationList(String memberId, Pageable pageable) {
        return jpaQueryFactory.selectFrom(notification)
                .where(notification.memberId.eq(memberId))
                .orderBy(notification.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Integer countingNotReadNotification(String memberId) {
        Long result = jpaQueryFactory.select(notification.count())
                .from(notification)
                .where(notification.memberId.eq(memberId),
                        notification.readStatus.eq(false))
                .fetchOne();
        return result.intValue();
    }

}
