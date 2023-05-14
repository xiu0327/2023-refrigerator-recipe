package refrigerator.back.authentication.infra;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.adapter.infra.jwt.TokenStatus;
import refrigerator.back.member.application.domain.MemberStatus;

@SpringBootTest
@Slf4j
class  JsonWebTokenProviderTest {

    @Autowired JsonWebTokenProvider jsonWebTokenProvider;

    @Test
    void 토큰_생성_파싱() throws InterruptedException {
        // given
        String username = "email123@gmail.com";
        // when
        String token = jsonWebTokenProvider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰 파싱했을 때, username 이 일치하는지 */
        Claims claims = jsonWebTokenProvider.parseClaims(token);
        Assertions.assertThat(claims.getSubject()).isEqualTo(username);
    }

    @Test
    void 토큰_유효성_확인() throws InterruptedException {
        // given
        String username = "email123@gmail.com";
        // when
        String token = jsonWebTokenProvider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰이 만료되었을 때 에러가 제대로 발생하는지 */
        Thread.sleep(2500);
        Assertions.assertThat(
                jsonWebTokenProvider.validateToken(token)).isEqualTo(TokenStatus.EXPIRED);
    }

    @Test
    void 토큰_유효시간_확인(){
        // given
        String username = "email123@gmail.com";
        // when
        String token = jsonWebTokenProvider.createToken(
                username,
                MemberStatus.STEADY_STATUS.toString(),
                2); // 토큰 유효 시간 5초
        // then
        /* 토큰 파싱했을 때, username 이 일치하는지 */
        Claims claims = jsonWebTokenProvider.parseClaims(token);
        log.info("date = {}", claims.getExpiration().toString());
    }

}