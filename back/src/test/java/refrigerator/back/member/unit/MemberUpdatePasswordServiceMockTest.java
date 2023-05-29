package refrigerator.back.member.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.PersistMemberPort;
import refrigerator.back.member.application.service.MemberUpdatePasswordService;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;

@ExtendWith(MockitoExtension.class)
class MemberUpdatePasswordServiceMockTest {

    @InjectMocks MemberUpdatePasswordService service;
    @Mock EncryptPasswordPort encryptPasswordPort;
    @Mock FindMemberPort findMemberPort;

    @Test
    @DisplayName("비밀번호 변경 성공")
    void updatePasswordSuccess() {
        // given
        String email = "email123@gmail.com";
        String oldPassword = "password123!";
        String newPassword = "password123@";
        given(findMemberPort.findMemberNotUseCache(email))
                .willReturn(Member.join(email, oldPassword, "닉네임"));
        given(encryptPasswordPort.encrypt(newPassword))
                .willReturn("암호화 된 새로운 password");
        // when
        String result = service.updatePassword(email, newPassword);
        // then
        assertThat(result).isEqualTo("암호화 된 새로운 password");
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 회원이 존재하지 않음")
    void updatePasswordFail_NotExistMember() {
        // given
        String email = "email123@gmail.com";
        String newPassword = "password123!";
        given(findMemberPort.findMemberNotUseCache(email))
                .willReturn(null);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                service.updatePassword(email, newPassword);
            }catch (BusinessException e){
                assertThat(e.getBasicExceptionType())
                        .isEqualTo(MemberExceptionType.NOT_FOUND_MEMBER);
                throw e;
            }
        });
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 기존 비밀번호와 동일")
    void updatePasswordFail_EqualOldPassword() {
        // given
        String email = "email123@gmail.com";
        String newPassword = "password123!";
        String oldPassword = "password123!";
        given(findMemberPort.findMemberNotUseCache(email))
                .willReturn(Member.join(email, oldPassword, "닉네임"));
        given(encryptPasswordPort.match(newPassword, oldPassword))
                .willReturn(true);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                service.updatePassword(email, newPassword);
            }catch (BusinessException e){
                assertThat(e.getBasicExceptionType())
                        .isEqualTo(MemberExceptionType.EQUAL_OLD_PASSWORD);
                throw e;
            }
        });
    }
}