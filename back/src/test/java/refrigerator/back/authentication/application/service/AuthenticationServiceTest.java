package refrigerator.back.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class AuthenticationServiceTest {

    @Autowired AuthenticationService authenticationService;
    @Autowired TestData testData;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JsonWebTokenProvider jsonWebTokenProvider;
    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Test
    void 로그인(){
        // given
        String email = "email123@gmail.com";
        String password = "password123";
        testData.createMemberByEmailAndPassword(email, passwordEncoder.encode(password));
        // when
        TokenDTO token = authenticationService.login(email, password);
        // then
        assertNotNull(token.getAccessToken());
    }

    @Test
    void 토큰_재발행() throws InterruptedException {
        // given
        String email = "email123@gmail.com";
        String password = "password123";
        String authority = MemberStatus.STEADY_STATUS.getStatusCode();
        testData.createMemberByEmailAndPassword(email, passwordEncoder.encode(password));
        // when
        String accessToken = jsonWebTokenProvider.createToken(email, authority, 300);
        String refreshToken = jsonWebTokenProvider.createToken(email, authority, 500);
        refreshTokenRepository.setData(email, refreshToken, 500);
        // then
        /* accessToken 유효 기간이 만료했을 때, accessToken 재발급 */
        Thread.sleep(350);
        TokenDTO newAccessToken = authenticationService.reissue(accessToken);
        assertNotNull(newAccessToken);
    }

    @Test
    void 토큰_재발행_실패() throws InterruptedException {
        // given
        String email = "email123@gmail.com";
        String password = "password123";
        String authority = MemberStatus.STEADY_STATUS.getStatusCode();
        testData.createMemberByEmailAndPassword(email, passwordEncoder.encode(password));
        // when
        String accessToken = jsonWebTokenProvider.createToken(email, authority, 300);
        String refreshToken = jsonWebTokenProvider.createToken(email, authority, 400);
        refreshTokenRepository.setData(email, refreshToken, 400);
        // then
        /* refreshToken 유효 기간이 만료했다면, 에러 발생 */
        Thread.sleep(450);
        assertThrows(BusinessException.class, () -> {
            try{
                authenticationService.reissue(accessToken);
            } catch (BusinessException e){
                log.info(e.getMessage());
                assertThat(e.getBasicExceptionType()).isEqualTo(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
                throw e;
            }
        });
    }

}