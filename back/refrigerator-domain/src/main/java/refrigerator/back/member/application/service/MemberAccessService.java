package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Currency;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;
    private final CurrentTime currentTime;

    // TODO : Member 내부에서 LocalDateTime.now()로 가입시간을 넣었는데 밖으로 빼고 service에서 currentTime을 주입해서 처리함
    
    @Override
    @Transactional
    public Long join(String email, String password, String nickname) {
        duplicateCheck(email);
        return createMemberPort.createMember(
                Member.join(
                        email,
                        encryptPasswordPort.encrypt(password),
                        nickname,
                        currentTime.now()));
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
