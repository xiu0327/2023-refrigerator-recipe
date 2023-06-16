package refrigerator.back.notification.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.notification.adapter.in.dto.NotificationListResponseDTO;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ReadNotificationReadStatusUseCase;


@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FindNotificationListUseCase findNotificationListUseCase;
    private final ReadNotificationReadStatusUseCase changeNotificationReadStatusUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/notifications")
    public NotificationListResponseDTO<NotificationResponseDTO> getNotificationList(@RequestParam("page") int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return new NotificationListResponseDTO<>(
                findNotificationListUseCase.getNotificationList(memberInformation.getMemberEmail(), page, size));
    }

    @PutMapping("/api/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void readNotification(@PathVariable("notificationId") Long notificationId){
        changeNotificationReadStatusUseCase.readNotification(notificationId);
    }

}
