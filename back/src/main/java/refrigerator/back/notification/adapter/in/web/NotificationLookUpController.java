package refrigerator.back.notification.adapter.in.web;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import refrigerator.back.notification.adapter.in.dto.NotificationListResponseDTO;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;

@Controller
public class NotificationLookUpController {

    FindNotificationListUseCase findNotificationListUseCase;

    // 알림 조회
    @GetMapping("/api/notifications/")
    public NotificationListResponseDTO<NotificationResponseDTO> getNotificationList(@RequestParam("size") int size,
                                                                                    @RequestParam(value = "page", defaultValue = "10") int page) {
        String email = "";
        return new NotificationListResponseDTO<>(findNotificationListUseCase.getNotificationList(email, size, page));
    }

    // 신규 알림 생성 조회
    @GetMapping("/api/notifications/sign")
    public boolean getNotificationSign() {
        String email = "";
        return findNotificationListUseCase.getNotificationSign(email);
    }
}
