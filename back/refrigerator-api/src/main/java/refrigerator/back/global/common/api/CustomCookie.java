package refrigerator.back.global.common.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface CustomCookie {
    Cookie create(String value);
    void delete(HttpServletResponse response);
    Cookie get(Cookie[] cookies);

    boolean isValid(Cookie target);

}
