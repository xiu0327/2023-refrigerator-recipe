package refrigerator.back.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.adapter.infra.jwt.TokenStatus;
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
        assertNotNull(token.getRefreshToken());
        assertThat(jsonWebTokenProvider.validateToken(token.getAccessToken())).isEqualTo(TokenStatus.PASS);
        assertThat(jsonWebTokenProvider.validateToken(token.getRefreshToken())).isEqualTo(TokenStatus.PASS);
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
        String refreshToken = jsonWebTokenProvider.createToken(email, authority, 1000 * 60);
        refreshTokenRepository.setData(email, refreshToken, 1000 * 60);
        // then
        /* accessToken 유효 기간이 만료 */
        Thread.sleep(350);
        assertThat(jsonWebTokenProvider.validateToken(accessToken)).isEqualTo(TokenStatus.EXPIRED);
        /* accessToken 재발급 */
        TokenDTO newAccessToken = authenticationService.reissue(accessToken, refreshToken);
        assertNotNull(newAccessToken.getAccessToken());
        assertNotNull(newAccessToken.getRefreshToken());
        assertThat(jsonWebTokenProvider.validateToken(newAccessToken.getAccessToken())).isEqualTo(TokenStatus.PASS);
        assertThat(jsonWebTokenProvider.validateToken(newAccessToken.getRefreshToken())).isEqualTo(TokenStatus.PASS);
    }

    @Test
    void 토큰_재발행_실패_토큰_모두_만료() throws InterruptedException {
        /* accessToken 만료, refreshToken 만료 -> 에러 발생 */
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
        Thread.sleep(450);
        assertThrows(BusinessException.class, () -> {
            try{
                authenticationService.reissue(accessToken, refreshToken);
            } catch (BusinessException e){
                log.info(e.getMessage());
                assertThat(jsonWebTokenProvider.validateToken(accessToken)).isEqualTo(TokenStatus.EXPIRED);
                assertThat(jsonWebTokenProvider.validateToken(refreshToken)).isEqualTo(TokenStatus.EXPIRED);
                assertThat(e.getBasicExceptionType()).isEqualTo(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
                throw e;
            }
        });
    }

    @Test
    void 토큰_재발행_실패_액세스_토큰_만료() throws InterruptedException {
        /* accessToken 유효, refreshToken 만료 -> 에러 발생 */
        // given
        String email = "email123@gmail.com";
        String password = "password123";
        String authority = MemberStatus.STEADY_STATUS.getStatusCode();
        testData.createMemberByEmailAndPassword(email, passwordEncoder.encode(password));
        // when
        String accessToken = jsonWebTokenProvider.createToken(email, authority, 300);
        String refreshToken = jsonWebTokenProvider.createToken(email, authority, 1000 * 5);
        refreshTokenRepository.setData(email, refreshToken, 1000 * 5);
        // then
        /* accessToken 만료, refreshToken 유효 */
        Thread.sleep(350);
        assertThat(jsonWebTokenProvider.validateToken(accessToken)).isEqualTo(TokenStatus.EXPIRED);
        assertThat(jsonWebTokenProvider.validateToken(refreshToken)).isEqualTo(TokenStatus.PASS);
        assertThat(refreshTokenRepository.getData(email)).isEqualTo(refreshToken);
        /* accessToken 유효, refreshToken 만료 */
        Thread.sleep(1000 * 5);
        String newAccessToken = jsonWebTokenProvider.createToken(email, authority, 1000 * 60);
        assertThrows(BusinessException.class, () -> {
            try{
                authenticationService.reissue(newAccessToken, refreshToken);
            }catch (BusinessException e){
                log.info(e.getMessage());
                assertThat(jsonWebTokenProvider.validateToken(newAccessToken)).isEqualTo(TokenStatus.PASS);
                assertThat(jsonWebTokenProvider.validateToken(refreshToken)).isEqualTo(TokenStatus.EXPIRED);
                assertThat(e.getBasicExceptionType()).isEqualTo(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
                throw e;
            }
        });
    }

}