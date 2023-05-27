package refrigerator.back.authentication.infra;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.authentication.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.infra.jwt.TokenStatus;
import refrigerator.back.authentication.infra.jwt.provider.JsonWebTokenProviderImpl;
import refrigerator.back.authentication.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.authentication.infra.jwt.JsonWebTokenKey.AUTHORITIES_KEY;

@SpringBootTest(classes = {JsonWebTokenProviderImpl.class})
@Slf4j
class  JsonWebTokenProviderTest {

    @Autowired JsonWebTokenProvider provider;

    @Test
    void 토큰_생성_파싱() throws InterruptedException {
        // given
        String username = "email123@gmail.com";
        // when
        String token = provider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰 파싱했을 때, username 이 일치하는지 */
        Claims claims = provider.parseClaims(token);
        Assertions.assertThat(claims.getSubject()).isEqualTo(username);
    }

    @Test
    void 토큰_유효성_확인() throws InterruptedException {
        // given
        String username = "email123@gmail.com";
        // when
        String token = provider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰이 만료되었을 때 에러가 제대로 발생하는지 */
        Thread.sleep(2500);
        Assertions.assertThat(
                provider.validateToken(token)).isEqualTo(TokenStatus.EXPIRED);
    }

    @Test
    void 토큰_유효시간_확인(){
        // given
        String username = "email123@gmail.com";
        // when
        String token = provider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰 파싱했을 때, username 이 일치하는지 */
        Claims claims = provider.parseClaims(token);
        log.info("date = {}", claims.getExpiration().toString());
    }

    @Test
    void JWT_필터_로직_테스트(){
        String username = "username";
        String token = provider.createToken(username, "authority", 30000);
        if (token != null && provider.validateToken(token) == TokenStatus.PASS){
            Claims claims = provider.parseClaims(token);
            if (claims.get(AUTHORITIES_KEY) == null || !hasText(claims.get(AUTHORITIES_KEY).toString())){
                throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY);
            }
            Authentication authentication = new EmailAuthenticationToken(
                    claims.getSubject(),
                    "",
                    Set.of(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString()))
            );
            assertThat(authentication.getName()).isEqualTo(username);
            assertThat(authentication.getAuthorities().size()).isEqualTo(1);
        }
    }

}