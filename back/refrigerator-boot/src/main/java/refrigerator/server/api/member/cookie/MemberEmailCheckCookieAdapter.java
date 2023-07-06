package refrigerator.server.api.member.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.servlet.http.Cookie;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MemberEmailCheckCookieAdapter {
    private final String cookieName = "Email-Check";
    private final String cookieValue = "true";
    private final String cookiePath = "/api/members/join";
    private final int cookieMaxAge = 60 * 30;

    public Cookie create(){
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieMaxAge); // 30분
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    public boolean isExist(Cookie[] cookies){
        return Arrays.stream(cookies).anyMatch(this::isValid);
    }

    public Cookie delete(){
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/api/members/join");
        cookie.setMaxAge(0); // 30분
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    public boolean isValid(Cookie cookie){
        return cookie.getName().equals(cookieName)
                && cookie.getValue().equals(cookieValue)
                && cookie.getSecure()
                && cookie.getMaxAge() == cookieMaxAge
                && cookie.getPath().equals(cookiePath)
                && cookie.isHttpOnly();
    }
}
