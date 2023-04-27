package refrigerator.back.notification.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.notification.adapter.mapper.NotificationMapper;
import refrigerator.back.notification.adapter.out.repository.NotificationRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.out.ReadNotificationPort;
import refrigerator.back.notification.application.port.out.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.UpdateNotificationReadStatusPort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements ReadNotificationPort, UpdateNotificationReadStatusPort, SaveNotificationPort {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationList(String email, int page, int size) {
        return notificationRepository.findByMemberIdOrderByCreateDateDesc(email, PageRequest.of(page, size));
    }

    @Override
    public Notification getNotification(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void updateReadStatus(Long notificationId, boolean status) {
        notificationRepository.updateReadStatus(notificationId, status);
    }

    @Override
    public Long saveNotification(Notification notification) {
        return notificationRepository.save(notification).getId();
    }
}
