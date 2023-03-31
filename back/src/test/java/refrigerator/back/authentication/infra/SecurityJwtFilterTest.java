package refrigerator.back.authentication.infra;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import refrigerator.back.authentication.adapter.infra.jwt.TokenStatus;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.adapter.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.authentication.adapter.infra.jwt.JsonWebTokenKey.AUTHORITIES_KEY;

@SpringBootTest
@Slf4j
public class SecurityJwtFilterTest {

    @Autowired JsonWebTokenProvider provider;

    @Test
    void 필터_로직_테스트(){
        String username = "username";
        String token = provider.createToken(username, "authority", 30000);
        if (token != null && provider.validateToken(token) == TokenStatus.PASS){
            Claims claims = provider.parseClaims(token);
            if (claims.get(AUTHORITIES_KEY) == null || !hasText(claims.get(AUTHORITIES_KEY).toString())){
                throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY);
            }
            claims.get(AUTHORITIES_KEY);
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
