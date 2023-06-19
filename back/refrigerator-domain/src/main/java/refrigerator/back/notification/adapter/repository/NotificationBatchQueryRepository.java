package refrigerator.back.notification.adapter.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.application.domain.NotificationType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static refrigerator.back.notification.application.domain.QNotification.notification;
import static refrigerator.back.notification.exception.NotificationExceptionType.*;

@Repository
@RequiredArgsConstructor
public class NotificationBatchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Transactional
    public Long deleteNotification(LocalDateTime date) {

        long row = jpaQueryFactory.delete(notification)
                .where(
                        notification.type.eq(NotificationType.EXPIRATION_DATE),
                        nullSafeBuilder(() -> notification.createDate.lt(date))
                )
                .execute();

        em.flush();
        em.clear();

        return row;
    }

    @Transactional
    public Long deleteDeadlineNotification(LocalDateTime date) {

        long row = jpaQueryFactory.delete(notification)
                .where(
                        notification.type.ne(NotificationType.EXPIRATION_DATE),
                        nullSafeBuilder(() -> notification.createDate.lt(date))
                ).execute();

        em.flush();
        em.clear();

        return row;
    }

    @Transactional
    public Long deleteTestNotification(LocalDateTime date) {

        long row = jpaQueryFactory.delete(notification)
                .where(
                        nullSafeBuilder(() -> notification.createDate.goe(date))
                ).execute();

        em.flush();
        em.clear();

        return row;
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(NOTIFICATION_DELETE_FAIL);
        }
    }
}
