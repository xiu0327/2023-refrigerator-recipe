package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;

@Service
@RequiredArgsConstructor
public class MemberWithdrawService implements WithdrawMemberUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final FindMemberPort findMemberPort;

    @Override
    @Transactional
    public void withdrawMember(String email) {
        Member member = findMemberPort.findMember(email);
        member.withdraw();
        updateMemberPort.update(member);
    }
}
