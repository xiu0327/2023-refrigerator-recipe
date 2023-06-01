package refrigerator.back.notification.adapter.out.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.notification.adapter.out.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQueryRepository {
}
