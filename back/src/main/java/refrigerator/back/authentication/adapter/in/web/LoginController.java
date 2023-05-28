package refrigerator.back.authentication.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.LogoutUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginUseCase loginUseCase;
    @Value("${oauth.password}")
    private String oauthPassword;
    @Value("${front.domain}")
    private String frontDomain;

    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO loginByBasic(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        return login(request.getEmail(), request.getPassword(), response);
    }
    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginByOAuth2(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
        try{
            TokenDTO token = login(email, oauthPassword, response);
            response.sendRedirect(frontDomain + "/member/success?token=" + token.getAccessToken());
        } catch (BusinessException e){
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setCharacterEncoding("UTF-8");
            response.sendRedirect(frontDomain + "/member/fail?errorCode="
                    + e.getBasicExceptionType().getErrorCode());
        }
    }

    private TokenDTO login(String email, String password, HttpServletResponse response) {
        TokenDTO token = loginUseCase.login(email, password);
        Cookie cookie = new Cookie("Refresh-Token", token.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600 * 24 * 30);
        cookie.setPath("/api/auth/reissue");
        response.addCookie(cookie);
        token.removeRefreshToken();
        return token;
    }
}
