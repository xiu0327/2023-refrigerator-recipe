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
import refrigerator.back.member.application.port.out.PersistMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, FindPasswordUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;
    private final PersistMemberPort updateMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;
    private final CreateTokenPort createTokenPort;

    @Override
    @Transactional
    public Long join(String email, String password, String nickname) {
        duplicateCheck(email);
        return createMemberPort.createMember(
                Member.join(
                        email,
                        encryptPasswordPort.encrypt(password),
                        nickname));

    }

    @Override
    public void duplicateCheck(String email) {
        if (findMemberPort.findMemberNotUseCache(email) != null){
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
        Member member = findMemberPort.findMemberNotUseCache(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        member.updatePassword(encryptPasswordPort.encrypt(newPassword));
        updateMemberPort.persist(member);
    }
}
