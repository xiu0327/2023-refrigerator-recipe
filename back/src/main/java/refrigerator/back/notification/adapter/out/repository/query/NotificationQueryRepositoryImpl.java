package refrigerator.back.notification.adapter.out.repository.query;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.domain.QNotification;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static refrigerator.back.ingredient.application.domain.QIngredient.ingredient;
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

        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(notification.type.eq(NotificationType.EXPIRATION_DATE)).then(2)
                .otherwise(1);

        return jpaQueryFactory.selectFrom(notification)
                .where(notification.memberId.eq(memberId))
                .orderBy(rankPath.desc() , notification.createDate.desc())
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
