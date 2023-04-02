package refrigerator.back.member.adapter.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;

@SpringBootTest
@Slf4j
public class RefreshTokenRepositoryTest {

    @Autowired RefreshTokenRepository refreshTokenRepository;

    @Test
    void 만료된_토큰_조회() throws InterruptedException {
        // given
        String email = "email@gmail.com";
        String token = "token";
        // when
        refreshTokenRepository.setData(email, token, 300);
        // then
        /* 유효 기간이 지났을 때, Redis 에서 자동 삭제 됨 -> 없은 데이터를 조회할 경우 null 반환 */
        Thread.sleep(400);
        String data = refreshTokenRepository.getData(email);
        Assertions.assertThat(data).isNull();;
    }
}
