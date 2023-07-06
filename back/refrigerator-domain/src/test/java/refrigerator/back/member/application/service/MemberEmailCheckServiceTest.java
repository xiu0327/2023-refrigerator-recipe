package refrigerator.back.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.out.FindMemberCountPort;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberEmailCheckServiceTest {

    @InjectMocks MemberEmailCheckService service;
    @Mock FindMemberCountPort findMemberCountPort;

    @Test
    @DisplayName("이메일 중복 확인 성공 테스트")
    void duplicateCheck() {
        // given
        String email = "email";
        BDDMockito.given(findMemberCountPort.countByEmail(email))
                .willReturn(0);
        // when & then
        assertDoesNotThrow(() -> service.duplicateCheck(email));
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 테스트")
    void duplicateCheckFailTest() {
        // given
        String email = "email";
        BDDMockito.given(findMemberCountPort.countByEmail(email))
                .willReturn(1);
        // when & then
        assertThrows(BusinessException.class, () -> {
            try{
                service.duplicateCheck(email);
            } catch (BusinessException e){
                assertEquals(MemberExceptionType.DUPLICATE_EMAIL, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}