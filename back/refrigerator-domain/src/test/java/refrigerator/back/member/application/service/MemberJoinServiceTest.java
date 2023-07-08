package refrigerator.back.member.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.common.RandomNumber;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MemberJoinServiceTest {

    @InjectMocks MemberJoinService service;
    @Mock CreateMemberPort createMemberPort;
    @Mock CurrentTime<LocalDateTime> currentTime;
    @Mock RandomNumber<Integer> randomNumber;


    @Test
    @DisplayName("회원 가입 성공 테스트")
    void join() {
        // given
        long memberId = 1L;
        BDDMockito.given(randomNumber.getNumber())
                .willReturn(2);
        BDDMockito.given(createMemberPort.createMember(any()))
                .willReturn(memberId);
        BDDMockito.given(currentTime.now())
                .willReturn(LocalDateTime.of(2023, 7, 6, 3, 42));
        // when
        Long result = service.join("email", "password", "nickname");
        // then
        assertEquals(memberId, result);
    }

    @Test
    @DisplayName("회원 가입 실패 테스트")
    void joinFailTest() {
        // given
        BDDMockito.given(randomNumber.getNumber())
                .willReturn(7);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                service.join("email", "password", "nickname");
            }catch (BusinessException e){
                assertEquals(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}