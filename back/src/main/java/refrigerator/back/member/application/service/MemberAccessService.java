package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, FindPasswordUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;

    @Override
    public Long join(String email, String password, String nickname) {
        duplicateCheck(email);
        return createMemberPort.createMember(MemberDomain.join(email, password, nickname));
    }

    @Override
    public void duplicateCheck(String email) {
        if (findMemberPort.findMember(email) != null){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }

    @Override
    public void findPassword(String email) {
        if (findMemberPort.findMember(email) == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        MemberDomain member = findMemberPort.findMember(email);
        member.updatePassword(newPassword);
    }
}
