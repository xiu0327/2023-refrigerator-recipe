package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.InitMemberProfileAndNicknamePort;
import refrigerator.back.member.application.port.out.PersistMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberService implements FindMemberInfoUseCase, CheckFirstLoginUseCase {

    private final FindMemberPort findMemberPort;

    @Override
    @Transactional(readOnly = true)
    public Member findMember(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return member;
    }

    @Override
    @Transactional(readOnly = true)
    public Member pureFindMemberByEmail(String email) {
        return findMemberPort.findMemberNotUseCache(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkFirstLogin(String memberId) {
        Member member = findMemberPort.findMember(memberId);
        return !StringUtils.hasText(member.getNickname()) || member.getProfile() == MemberProfileImage.PROFILE_NOT_SELECT;
    }

}
