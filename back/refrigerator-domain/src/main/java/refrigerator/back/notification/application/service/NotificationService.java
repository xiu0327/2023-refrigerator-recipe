package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ReadNotificationReadStatusUseCase;
import refrigerator.back.notification.application.port.out.read.FindNotificationListPort;
import refrigerator.back.notification.application.port.out.write.UpdateNotificationReadStatusPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements FindNotificationListUseCase, ReadNotificationReadStatusUseCase {
    private final UpdateNotificationReadStatusPort updateNotificationReadStatusPort;
    private final NotificationTimeService notificationTimeService;
    private final FindNotificationListPort findNotificationListPort;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationList(String email, int page, int size) {
        List<Notification> notifications = findNotificationListPort.findNotificationList(email, page, size);
        return notifications.stream()
                .map(this::mapping)
                .collect(Collectors.toList());
    }

    private NotificationResponseDTO mapping(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .path(notification.getPath())
                .message(notification.getMessage())
                .readStatus(notification.isReadStatus())
                .type(notification.getType().toString())
                .registerTime(notificationTimeService.replace(notification.getCreateDate()))
                .build();
    }

    @Override
    @Transactional
    public void readNotification(Long id) {
        updateNotificationReadStatusPort.updateReadStatus(id, true);
    }
}