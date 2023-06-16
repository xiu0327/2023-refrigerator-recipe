package refrigerator.back.notification.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.api.BasicListResponseDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ReadNotificationReadStatusUseCase;

import static refrigerator.back.global.common.api.MemberInformation.getMemberEmail;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FindNotificationListUseCase findNotificationListUseCase;
    private final ReadNotificationReadStatusUseCase changeNotificationReadStatusUseCase;

    @GetMapping("/api/notifications")
    public BasicListResponseDTO<NotificationDTO> getNotificationList(@RequestParam("page") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return new BasicListResponseDTO<>(
                findNotificationListUseCase.getNotificationList(getMemberEmail(), page, size));
    }

    @PutMapping("/api/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void readNotification(@PathVariable("notificationId") Long notificationId){
        changeNotificationReadStatusUseCase.readNotification(notificationId);
    }

}
