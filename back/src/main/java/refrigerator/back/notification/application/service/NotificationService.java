package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ChangeNotificationReadStatusUseCase;
import refrigerator.back.notification.application.port.out.ReadNotificationPort;
import refrigerator.back.notification.application.port.out.SaveNotificationPort;
import refrigerator.back.notification.application.port.out.UpdateNotificationReadStatusPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService implements FindNotificationListUseCase, ChangeNotificationReadStatusUseCase {
    private final UpdateNotificationReadStatusPort updateNotificationReadStatusPort;
    private final ReadNotificationPort readNotificationPort;
    private final NotificationTimeService notificationTimeService;

    @Override
    public List<NotificationResponseDTO> getNotificationList(String email, int page, int size) {
        return readNotificationPort.getNotificationList(email, page, size).stream()
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
                .registerTime(notificationTimeService.replace(notification.getCreateDate())).build();
    }


    @Override
    public void readNotification(Long id) {
        updateNotificationReadStatusPort.updateReadStatus(id, true);
    }
}