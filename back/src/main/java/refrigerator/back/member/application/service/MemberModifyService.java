package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.InitNicknameAndProfileUseCase;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdatePasswordUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.out.*;
import refrigerator.back.member.exception.MemberExceptionType;

import static refrigerator.back.global.common.InputDataFormatCheck.NICKNAME_REGEX;
import static refrigerator.back.global.common.InputDataFormatCheck.inputCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements
        UpdateNicknameUseCase, UpdateProfileUseCase, InitNicknameAndProfileUseCase,
        UpdatePasswordUseCase {

    private final ModifyMemberNicknamePort modifyMemberNicknamePort;
    private final ModifyMemberProfilePort modifyMemberProfilePort;
    private final InitMemberProfileAndNicknamePort initMemberProfileAndNicknamePort;
    private final PersistMemberPort persistMemberPort;
    private final EncryptPasswordPort encryptPasswordPort;
    private final FindMemberPort findMemberPort;

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

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        Member member = findMemberPort.findMemberNotUseCache(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        Boolean isMatchPassword = encryptPasswordPort.match(newPassword, member.getPassword());
        if (isMatchPassword){
            throw new BusinessException(MemberExceptionType.EQUAL_OLD_PASSWORD);
        }
        member.updatePassword(encryptPasswordPort.encrypt(newPassword));
        persistMemberPort.persist(member);
    }

}