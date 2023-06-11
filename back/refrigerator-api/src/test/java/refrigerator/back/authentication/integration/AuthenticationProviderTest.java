package refrigerator.back.authentication.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.member.application.domain.MemberStatus;

import java.util.Set;

@SpringBootTest
@Transactional
public class AuthenticationProviderTest {

    @Autowired AuthenticationProvider provider;

    @Test
    void 이메일_인증_공급자_테스트(){
        // given
        String email = "mstest102@gmail.com";
        String password = "password123!";
        // when
        /* 사용자가 클라이언트를 통해 email, password 입력 -> 인증 객체 변환 */
        Authentication authenticate = provider.authenticate(
                new EmailAuthenticationToken(
                        email,
                        password,
                        Set.of(new SimpleGrantedAuthority(
                                MemberStatus.STEADY_STATUS.getStatusCode())
                        )
                ));
        // then
        Assertions.assertThat(authenticate.getName()).isEqualTo(email);
    }
}
