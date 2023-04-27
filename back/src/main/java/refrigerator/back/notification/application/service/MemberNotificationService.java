package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import refrigerator.back.notification.application.port.in.ChangeMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.CreateMemberNotificationUseCase;
import refrigerator.back.notification.application.port.in.GetMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.out.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.ModifyMemberNotificationPort;

@Service
@RequiredArgsConstructor
public class MemberNotificationService implements CreateMemberNotificationUseCase, GetMemberNotificationSignUseCase, ChangeMemberNotificationSignUseCase {

    private final CreateMemberNotificationPort createMemberNotificationPort;
    private final FindMemberNotificationSignPort findMemberNotificationSignPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Override
    public void createMemberNotification(String memberId) {
        createMemberNotificationPort.create(memberId);
    }

    @Override
    public Boolean getSign(String memberId) {
        return findMemberNotificationSignPort.getSign(memberId);
    }

    @Override
    public void SignOff(String memberId) {
        modifyMemberNotificationPort.modify(memberId, false);
    }

    @Override
    public void SingOn(String memberId) {
        modifyMemberNotificationPort.modify(memberId, true);
    }
}
