package refrigerator.back.notification.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationQueryRepository {
    void updateReadStatus(Long notificationId, boolean status);
}
