package refrigerator.back.authentication.adapter.in.web.cookie;

import org.springframework.stereotype.Component;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.authentication.infra.jwt.TokenStatus;
import refrigerator.back.authentication.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.global.common.CustomCookie;
import refrigerator.back.global.exception.BusinessException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class RefreshTokenCookie implements CustomCookie {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final String NAME = "Refresh-Token";
    private final String PATH = "/api/auth/reissue";

    public RefreshTokenCookie(JsonWebTokenProvider jsonWebTokenProvider) {
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }

    @Override
    public Cookie create(String token) {
        Cookie cookie = new Cookie(NAME, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600 * 24 * 30); // 30 일
        cookie.setPath(PATH);
        return cookie;
    }

    @Override
    public void delete(HttpServletResponse response) {
        Cookie cookie = new Cookie(NAME, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath(PATH);
        response.addCookie(cookie);
    }

    @Override
    public Cookie get(Cookie[] cookies) {
        if (cookies == null){
            throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_COOKIE);
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NAME))
                .findAny()
                .orElseThrow(() -> new BusinessException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
    }

    @Override
    public boolean isValid(Cookie target) {
        return target.getName().equals(NAME) &&
                jsonWebTokenProvider.validateToken(target.getValue()).equals(TokenStatus.PASS);
    }

}
