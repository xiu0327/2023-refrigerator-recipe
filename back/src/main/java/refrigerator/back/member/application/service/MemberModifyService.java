package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.InitNicknameAndProfileUseCase;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.out.InitMemberProfileAndNicknamePort;
import refrigerator.back.member.application.port.out.ModifyMemberNicknamePort;
import refrigerator.back.member.application.port.out.ModifyMemberProfilePort;
import refrigerator.back.member.exception.MemberExceptionType;

import static refrigerator.back.global.common.InputDataFormatCheck.NICKNAME_REGEX;
import static refrigerator.back.global.common.InputDataFormatCheck.inputCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements UpdateNicknameUseCase, UpdateProfileUseCase, InitNicknameAndProfileUseCase {

    private final ModifyMemberNicknamePort modifyMemberNicknamePort;
    private final ModifyMemberProfilePort modifyMemberProfilePort;
    private final InitMemberProfileAndNicknamePort initMemberProfileAndNicknamePort;

    @Override
    @Transactional
    public void updateNickname(String email, String newNickname) {
        inputCheck(NICKNAME_REGEX, newNickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        modifyMemberNicknamePort.modifyNickname(email, newNickname);
    }

    @Override
    @Transactional
    public void updateProfile(String email, String newProfileName) {
        modifyMemberProfilePort.modifyProfile(email, MemberProfileImage.findImageByName(newProfileName));
    }

    @Override
    @Transactional
    public void initNicknameAndProfile(String email, String nickname, String imageFileName) {
        InputDataFormatCheck.inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        initMemberProfileAndNicknamePort.initNicknameAndProfile(
                email,
                nickname,
                MemberProfileImage.findImageByName(imageFileName));
    }

}
