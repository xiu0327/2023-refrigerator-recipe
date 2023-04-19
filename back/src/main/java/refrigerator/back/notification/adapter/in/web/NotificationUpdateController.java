package refrigerator.back.notification.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import refrigerator.back.notification.application.port.in.ModifyNotificationUseCase;

@Controller
public class NotificationUpdateController {

    ModifyNotificationUseCase modifyNotificationUseCase;

    // 알림 수정 (안읽음 -> 읽음)
    @PutMapping("/api/notifications/{notificationId}")
    public void modifyNotification(@PathVariable("notificationId") Long id) {
        modifyNotificationUseCase.modifyNotification(id);
    }
}
