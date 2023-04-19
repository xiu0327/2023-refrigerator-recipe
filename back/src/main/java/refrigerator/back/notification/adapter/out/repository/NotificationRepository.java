package refrigerator.back.notification.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import refrigerator.back.notification.adapter.out.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long>, NotificationQueryRepository {

    List<Notifications> findByEmailOrderByCreateTimeDesc(String email, Pageable pageable);

    @Query("select mn from MemberNotification mn where mn.email = :email")
    MemberNotification findMemberNotification(@Param("email") String email);
}
