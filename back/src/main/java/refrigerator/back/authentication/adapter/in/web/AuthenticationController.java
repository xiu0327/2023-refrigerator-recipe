package refrigerator.back.authentication.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.ReissueTokenRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.LogoutUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final LoginUseCase loginUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;
    private final LogoutUseCase logoutUseCase;
    @Value("${oauth.password}")
    private String oauthPassword;
    @Value("${front.domain}")
    private String frontDomain;

    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO loginByBasic(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        return login(request.getEmail(), request.getPassword(), response);
    }

    @GetMapping("/api/auth/logout")
//    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void logout(HttpServletRequest request, HttpServletResponse response){
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        logoutUseCase.logout(accessToken);
        Cookie cookie = new Cookie("Refresh-Token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }

    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public void loginByOAuth2(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
        TokenDTO token = login(email, oauthPassword, response);
        response.sendRedirect(frontDomain + "/membermanagement/success?token=" + token.getAccessToken());
    }

    private TokenDTO login(String email, String password, HttpServletResponse response) {
        TokenDTO token = loginUseCase.login(email, password);
        Cookie cookie = new Cookie("Refresh-Token", token.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return token;
    }

    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO reissue(@RequestBody ReissueTokenRequestDTO request){
        return tokenReissueUseCase.reissue(request.getRefreshToken());
    }

}
