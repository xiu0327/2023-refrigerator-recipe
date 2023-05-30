package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;

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
    @Transactional(readOnly = true)
    public void duplicateCheck(String email) {
        Member findMember = findMemberPort.findMemberNotUseCache(email);
        if (findMember != null){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }

}
