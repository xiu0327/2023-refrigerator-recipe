package refrigerator.back.member.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired EncryptPasswordPort encryptPasswordPort;
    @Autowired TestData testData;

    @Test
    @DisplayName("닉네임 수정")
    void updateNickname() {
        String email = testData.createMemberByEmail("email@gmail.com");
        String nickname = "수정닉네임";
        memberService.updateNickname(email, nickname);
        String findNickname = testData.findMemberByEmail(email).getNickname();
        Assertions.assertThat(findNickname).isEqualTo(nickname);
    }

    @Test
    @DisplayName("프로필 수정")
    void updateProfile() {
        String email = testData.createMemberByEmail("email@gmail.com");
        String profileImage = "IMG_9709.JPG";
        memberService.updateProfile(email, profileImage);
        String findProfile = testData.findMemberByEmail(email).getProfile().getName();
        Assertions.assertThat(findProfile).isEqualTo(profileImage);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawMember() {
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email@gmail.com", encryptPasswordPort.encrypt(password));
        memberService.withdrawMember(email, password);
        Assertions.assertThat(testData.findMemberByEmail(email).getMemberStatus()).isEqualTo(MemberStatus.LEAVE_STATUS);
    }

}