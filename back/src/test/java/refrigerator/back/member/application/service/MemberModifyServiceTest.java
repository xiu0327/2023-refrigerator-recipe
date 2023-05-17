package refrigerator.back.member.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.TestData;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberModifyServiceTest {

    @Autowired TestData testData;
    @Autowired UpdateNicknameUseCase updateNicknameUseCase;
    @Autowired UpdateProfileUseCase updateProfileUseCase;
    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired EncryptPasswordPort encryptPasswordPort;
    @Autowired FindMemberPort findMemberPort;

    @Test
    @DisplayName("닉네임 수정")
    void updateNickname() {
        String email = testData.createMemberByEmail("email@gmail.com");
        String nickname = "수정닉네임";
        updateNicknameUseCase.updateNickname(email, nickname);
        String findNickname = findMemberPort.findMember(email).getNickname();
        Assertions.assertThat(findNickname).isEqualTo(nickname);
    }

    @Test
    @DisplayName("프로필 수정")
    void updateProfile() {
        String email = testData.createMemberByEmail("email@gmail.com");
        String profileImage = "IMG_9709.JPG";
        updateProfileUseCase.updateProfile(email, profileImage);
        String findProfile = findMemberPort.findMember(email).getProfile().getName();
        Assertions.assertThat(findProfile).isEqualTo(profileImage);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawMember() {
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email@gmail.com", encryptPasswordPort.encrypt(password));
        withdrawMemberUseCase.withdrawMember(email);
        Member member = findMemberPort.findMember(email);
        Assertions.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.LEAVE_STATUS);
    }

}