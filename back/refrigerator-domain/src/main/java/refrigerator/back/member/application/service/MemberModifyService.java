package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.out.ModifyMemberNicknamePort;
import refrigerator.back.member.application.port.out.ModifyMemberProfilePort;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements UpdateNicknameUseCase, UpdateProfileUseCase {

    private final ModifyMemberNicknamePort modifyMemberNicknamePort;
    private final ModifyMemberProfilePort modifyMemberProfilePort;


    @Override
    public void updateNickname(String email, String newNickname) {

        modifyMemberNicknamePort.modifyNickname(email, newNickname);
    }

    @Override
    public void updateProfile(String email, String newProfileName) {
        modifyMemberProfilePort.modifyProfile(email, MemberProfileImage.findImageByName(newProfileName));
    }

}
