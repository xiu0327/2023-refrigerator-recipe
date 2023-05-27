package refrigerator.back.authentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.LogoutUseCase;
import refrigerator.back.authentication.application.port.out.CheckContainBlackListPort;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.authentication.application.port.out.FindRefreshTokenByEmailPort;
import refrigerator.back.global.TestData;
import refrigerator.back.member.application.port.in.JoinUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LogoutServiceTest {

    @Autowired LoginUseCase loginUseCase;
    @Autowired LogoutUseCase logoutUseCase;
    @Autowired CheckContainBlackListPort checkContainBlackListPort;
    @Autowired FindRefreshTokenByEmailPort findRefreshTokenByEmailPort;

    private final String email = "nhtest@gmail.com";
    private final String password = "password123!";
    @Test
    @DisplayName("정상적인 로그아웃")
    void logout() {
        // given
        String accessToken = loginUseCase.login(email, password).getAccessToken();
        // when
        logoutUseCase.logout(accessToken);
        // then
        /*
        ------- check point -------
        1. accessToken 의 유효기간이 남아 있을 때, 해당 토큰이 블랙리스트에 등록이 되었는지
        2. refreshToken 이 DB 에서 삭제가 되었는지
         */
        Assertions.assertThat(checkContainBlackListPort.checkContainBlackList(accessToken)).isTrue();
        Assertions.assertThat(findRefreshTokenByEmailPort.findRefreshToken(email)).isNull();
    }
}