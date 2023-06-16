package refrigerator.back.authentication.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.global.common.api.CustomCookie;
import refrigerator.back.global.exception.domain.BusinessException;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class LoginController {

    private final LoginUseCase loginUseCase;
    private final CustomCookie refreshTokenCookie;
    private final String oauthPassword;
    private final String frontDomain;

    public LoginController(LoginUseCase loginUseCase,
                           @Qualifier("refreshTokenCookie") CustomCookie refreshTokenCookie,
                           @Value("${oauth.password}") String oauthPassword,
                           @Value("${front.domain}") String frontDomain) {
        this.loginUseCase = loginUseCase;
        this.refreshTokenCookie = refreshTokenCookie;
        this.oauthPassword = oauthPassword;
        this.frontDomain = frontDomain;
    }

    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO loginByEmail(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        return login(request.getEmail(), request.getPassword(), response);
    }

    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginByOAuth2(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
        try{
            TokenDTO token = login(email, oauthPassword, response);
            response.sendRedirect(frontDomain + "/member/success?token=" + token.getAccessToken());
        } catch (BusinessException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect(frontDomain + "/member/fail?errorCode="
                    + e.getBasicExceptionType().getErrorCode());
        }
    }

    private TokenDTO login(String email, String password, HttpServletResponse response) {
        TokenDTO token = loginUseCase.login(email, password);
        response.addCookie(refreshTokenCookie.create(token.getRefreshToken()));
        token.removeRefreshToken();
        return token;
    }
}
