package back.repository;

import back.domain.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


public interface NotificationRepository extends JpaRepository<Notifications, Long> {


    @Modifying
    @Transactional
    @Query("delete from Notifications n where n.type = 1 and n.createTime < :date")
    void deleteNotification(@Param("date") LocalDateTime date);

    @Modifying
    @Transactional
    @Query("delete from Notifications n where n.type != 1 and n.createTime < :date")
    void deleteDeadlineNotification(@Param("date") LocalDateTime date);
}
