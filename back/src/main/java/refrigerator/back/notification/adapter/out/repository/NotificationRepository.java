package refrigerator.back.notification.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.notification.adapter.out.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQueryRepository {
    List<Notification> findByMemberIdOrderByCreateDateDesc(String memberId, Pageable pageable);
}
