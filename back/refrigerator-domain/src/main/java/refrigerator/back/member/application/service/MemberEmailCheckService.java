package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.CheckEmailUseCase;
import refrigerator.back.member.application.port.out.FindMemberCountPort;
import refrigerator.back.member.exception.MemberExceptionType;


@Service
@RequiredArgsConstructor
public class MemberEmailCheckService implements CheckEmailUseCase {

    private final FindMemberCountPort findMemberCountPort;

    @Override
    public void isDuplicated(String email) {
        Integer result = findMemberCountPort.countByEmail(email);
        /* 같은 이메일을 가진 회원이 존재할 경우 */
        if (result != 0){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }

}
