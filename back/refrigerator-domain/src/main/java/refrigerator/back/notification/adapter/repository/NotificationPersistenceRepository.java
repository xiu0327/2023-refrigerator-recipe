package refrigerator.back.notification.adapter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.notification.application.domain.Notification;

public interface NotificationPersistenceRepository extends JpaRepository<Notification, Long>{
}
