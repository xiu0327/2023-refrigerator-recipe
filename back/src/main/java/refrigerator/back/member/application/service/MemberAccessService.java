package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberAccessService implements JoinUseCase, FindPasswordUseCase, DuplicateCheckEmailUseCase {

    private final CreateMemberPort createMemberPort;
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;

    @Override
    @Transactional
    public Long join(String email, String password, String nickname) {
        duplicateCheck(email);
        return createMemberPort.createMember(Member.join(email, password, nickname));
    }

    @Override
    public void duplicateCheck(String email) {
        if (findMemberPort.findMember(email) != null){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void findPassword(String email) {
        if (findMemberPort.findMember(email) == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        Member member = findMemberPort.findMember(email);
        member.updatePassword(newPassword);
        updateMemberPort.update(member);
    }
}
