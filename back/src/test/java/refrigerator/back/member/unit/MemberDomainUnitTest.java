package refrigerator.back.member.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberDomainUnitTest {

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() {
        String oldPassword = "password123!";
        String newPassword = "password124!";
        Member member = Member.join(
                "email123@gmail.com",
                oldPassword,
                "nickname");
        member.changePassword(newPassword);
        assertThat(member.isEqualPassword(newPassword)).isTrue();
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 기존 비밀번호와 동일")
    void changePasswordFail() {
        String oldPassword = "password123!";
        String newPassword = "password123!";
        Member member = Member.join(
                "email123@gmail.com",
                oldPassword,
                "nickname");
        assertThrows(BusinessException.class, () -> {
            try{
                member.changePassword(newPassword);
            }catch (BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(MemberExceptionType.EQUAL_OLD_PASSWORD);
                throw e;
            }
        });
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdraw() {
        Member member = Member.join(
                "email123@gmail.com",
                "password123!",
                "nickname");
        member.withdraw();
        assertThat(member.isWithdrawMember()).isTrue();
    }

}