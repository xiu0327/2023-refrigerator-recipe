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
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberModifyServiceTest {

    @Autowired TestData testData;
    @Autowired JoinUseCase joinUseCase;
    @Autowired UpdateNicknameUseCase updateNicknameUseCase;
    @Autowired UpdateProfileUseCase updateProfileUseCase;
    @Autowired UpdatePasswordUseCase updatePasswordUseCase;
    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired EncryptPasswordPort encryptPasswordPort;
    @Autowired FindMemberPort findMemberPort;

    @Test
    @DisplayName("닉네임 수정")
    void updateNickname() {
        String email = join();
        String nickname = "수정닉네임";
        updateNicknameUseCase.updateNickname(email, nickname);
        String findNickname = findMemberPort.findMember(email).getNickname();
        Assertions.assertThat(findNickname).isEqualTo(nickname);
    }

    @Test
    @DisplayName("프로필 수정")
    void updateProfile() {
        String email = join();
        String profileImage = "IMG_9709.JPG";
        updateProfileUseCase.updateProfile(email, profileImage);
        String findProfile = findMemberPort.findMember(email).getProfile().getName();
        Assertions.assertThat(findProfile).isEqualTo(profileImage);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawMember() {
        String email = join();
        withdrawMemberUseCase.withdrawMember(email);
        Member member = findMemberPort.findMember(email);
        Assertions.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.LEAVE_STATUS);
    }

    @Test
    @DisplayName("비밀번호 변경 시 기존 비밀번호와 동일할 때 에러 발생")
    void updatePasswordFail(){
        String email = join();
        String newPassword = "password123!";
        assertThrows(BusinessException.class, () -> {
            try{
                updatePasswordUseCase.updatePassword(email, newPassword);
            }catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(MemberExceptionType.EQUAL_OLD_PASSWORD);
                throw e;
            }
        });
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void updatePasswordSuccess(){
        String email = join();
        String newPassword = "password12334!";
        updatePasswordUseCase.updatePassword(email, newPassword);
        Member findMember = testData.findMemberByEmail(email);
        assertThat(encryptPasswordPort.match(newPassword, findMember.getPassword())).isTrue();
    }

    private String join() {
        String email = "email@gmail.com";
        String password = "password123!";
        joinUseCase.join(email, password, "닉네임");
        return email;
    }

}