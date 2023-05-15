package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import static refrigerator.back.global.common.InputDataFormatCheck.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements
        UpdateNicknameUseCase, UpdateProfileUseCase, FindMemberInfoUseCase,
        CheckFirstLoginUseCase, InitNicknameAndProfileUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final FindMemberPort findMemberPort;

    @Override
    @Transactional
    public void updateNickname(String email, String newNickname) {
        inputCheck(NICKNAME_REGEX, newNickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        updateMemberPort.updateNickname(email, newNickname);
    }

    @Override
    @Transactional
    public void updateProfile(String email, String newProfileName) {
        updateMemberPort.updateProfile(email, MemberProfileImage.findImageByName(newProfileName));
    }

    @Override
    public Member findMember(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return member;
    }

    @Override
    public Member pureFindMemberByEmail(String email) {
        return findMemberPort.findMember(email);
    }

    @Override
    public Boolean checkFirstLogin(String memberId) {
        Member member = findMemberPort.findMember(memberId);
        return !StringUtils.hasText(member.getNickname()) || member.getProfile() == MemberProfileImage.PROFILE_NOT_SELECT;
    }

    @Override
    @Transactional
    public void initNicknameAndProfile(String email, String nickname, String imageFileName) {
        InputDataFormatCheck.inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        updateMemberPort.initNicknameAndProfile(email, nickname, MemberProfileImage.findImageByName(imageFileName));
    }
}
