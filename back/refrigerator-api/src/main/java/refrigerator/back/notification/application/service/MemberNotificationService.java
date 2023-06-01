package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.notification.application.port.in.CreateMemberNotificationUseCase;
import refrigerator.back.notification.application.port.in.GetMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.UpdateMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.out.read.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.write.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.write.ModifyMemberNotificationPort;

@Service
@RequiredArgsConstructor
public class MemberNotificationService implements
        CreateMemberNotificationUseCase, GetMemberNotificationSignUseCase, UpdateMemberNotificationSignUseCase {

    private final CreateMemberNotificationPort createMemberNotificationPort;
    private final FindMemberNotificationSignPort findMemberNotificationSignPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Override
    public void createMemberNotification(String memberId) {
        createMemberNotificationPort.create(memberId);
    }

    @Override
    public Boolean getSign(String memberId) {
        Boolean result = findMemberNotificationSignPort.getSign(memberId);
        if (result == null){
            createMemberNotification(memberId);
            return false;
        }
        return result;
    }

    @Override
    public void updateNotification(String memberId) {
        modifyMemberNotificationPort.modify(memberId, false);
    }
}
