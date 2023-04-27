package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.notification.application.service.NotificationService;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, FindPasswordUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;
    private final CreateTokenPort createTokenPort;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Long join(String email, String password, String nickname) {
        duplicateCheck(email);

        Long memberId = createMemberPort.createMember(
                Member.join(
                        email,
                        encryptPasswordPort.encrypt(password),
                        nickname));

        notificationService.createMemberNotification(email);

        return memberId;

    }

    @Override
    public void duplicateCheck(String email) {
        if (findMemberPort.findMember(email) != null){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String findPassword(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return createTokenPort.createTokenWithDuration(
                email,
                member.getMemberStatus().getStatusCode(),
                1000 * 60 * 10);
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        member.updatePassword(encryptPasswordPort.encrypt(newPassword));
        updateMemberPort.update(member);
    }
}