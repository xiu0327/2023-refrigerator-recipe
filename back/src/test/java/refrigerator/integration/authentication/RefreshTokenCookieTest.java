package refrigerator.integration.authentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import refrigerator.back.authentication.application.port.external.JsonWebTokenProvider;
import refrigerator.back.global.common.CustomCookie;

import refrigerator.integration.authentication.jwt.provider.JsonWebTokenProviderImpl;
import refrigerator.integration.authentication.web.cookie.RefreshTokenCookie;

import javax.servlet.http.Cookie;

@SpringBootTest(classes = {JsonWebTokenProviderImpl.class})
class RefreshTokenCookieTest {

    CustomCookie refreshTokenCookie = new RefreshTokenCookie();
    @Autowired JsonWebTokenProvider jsonWebTokenProvider;
    Cookie[] cookies = null;
    String token = null;

    @BeforeEach
    void given(){
        token = jsonWebTokenProvider.createToken("email", "authority", 6000);
        cookies = new Cookie[]{refreshTokenCookie.create(token)};
    }

    @Test
    @DisplayName("쿠키 가져오기 성공 -> Refresh-Token 이 맞는지 확인")
    void getCookieSuccess() {
        Cookie cookie = refreshTokenCookie.get(cookies);
        Assertions.assertThat(refreshTokenCookie.isValid(jsonWebTokenProvider, cookie)).isTrue();
    }

}