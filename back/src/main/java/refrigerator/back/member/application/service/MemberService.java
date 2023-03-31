package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;

@Service
@RequiredArgsConstructor
public class MemberService implements UpdateNicknameUseCase, UpdateProfileUseCase, WithdrawMemberUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final FindMemberPort findMemberPort;

    @Override
    @Transactional
    public void updateNickname(String email, String newNickname) {
        Member member = findMemberPort.findMember(email);
        member.updateNickname(newNickname);
        updateMemberPort.update(member);
    }

    @Override
    @Transactional
    public void updateProfile(String email, String newProfileName) {
        Member member = findMemberPort.findMember(email);
        member.updateProfile(newProfileName);
        updateMemberPort.update(member);
    }

    @Override
    @Transactional
    public void withdrawMember(String email, String password) {
        Member member = findMemberPort.findMember(email);
        member.withdraw(password);
        updateMemberPort.update(member);
    }
}
