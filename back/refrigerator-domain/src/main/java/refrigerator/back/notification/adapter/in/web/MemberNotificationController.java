package refrigerator.back.notification.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.notification.adapter.in.dto.NotificationSignDTO;
import refrigerator.back.notification.application.port.in.GetMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.UpdateMemberNotificationSignUseCase;

@RestController
@RequiredArgsConstructor
public class MemberNotificationController {

    private final GetMemberNotificationSignUseCase getMemberNotificationSignUseCase;
    private final UpdateMemberNotificationSignUseCase updateMemberNotificationSignUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/notifications/sign")
    public NotificationSignDTO getNotificationSign(){
        return NotificationSignDTO.builder()
                .sign(getMemberNotificationSignUseCase.getSign(memberInformation.getMemberEmail()))
                .build();
    }

    @PutMapping("/api/notification/sign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void TurnOffNotificationSign(){
        updateMemberNotificationSignUseCase.updateNotification(memberInformation.getMemberEmail());
    }
}
