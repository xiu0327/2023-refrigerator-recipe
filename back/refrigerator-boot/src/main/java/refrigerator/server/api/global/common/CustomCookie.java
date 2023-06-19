package refrigerator.server.api.global.common;

import refrigerator.back.authentication.application.port.external.JsonWebTokenProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface CustomCookie {
    Cookie create(String value);
    void delete(HttpServletResponse response);
    Cookie get(Cookie[] cookies);

    boolean isValid(JsonWebTokenProvider provider, Cookie target);

}
