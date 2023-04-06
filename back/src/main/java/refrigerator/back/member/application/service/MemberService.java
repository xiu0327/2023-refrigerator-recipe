package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.FindMemberInfoUseCase;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberService implements UpdateNicknameUseCase, UpdateProfileUseCase,
        WithdrawMemberUseCase, FindMemberInfoUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final FindMemberPort findMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;
    private final MemberProfileImageService memberProfileImageService;

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
        if (!encryptPasswordPort.match(password, member.getPassword())){
            throw new BusinessException(AuthenticationExceptionType.NOT_EQUAL_PASSWORD);
        }
        member.withdraw();
        updateMemberPort.update(member);
    }

    @Override
    public Member findMember(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return member;
    }
}
