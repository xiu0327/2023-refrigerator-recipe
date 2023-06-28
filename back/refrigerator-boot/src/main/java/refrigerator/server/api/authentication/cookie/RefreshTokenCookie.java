package refrigerator.server.api.authentication.cookie;

import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;
import refrigerator.server.security.exception.JsonWebTokenException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


public class RefreshTokenCookie implements CustomCookie {

    private final String NAME = "Refresh-Token";
    private final String PATH = "/api/auth/reissue";


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
            throw new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_COOKIE);
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NAME))
                .findAny()
                .orElseThrow(() -> new JsonWebTokenException(AuthenticationExceptionType.NOT_FOUND_TOKEN));
    }

    @Override
    public boolean isValid(JsonWebTokenUseCase provider, Cookie target) {
        return target.getName().equals(NAME) &&
                provider.getTokenStatus(target.getValue()).equals(TokenStatus.PASS);
    }

}