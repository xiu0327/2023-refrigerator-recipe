package refrigerator.back.authentication.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.adapter.in.dto.LoginRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.ReissueTokenRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final LoginUseCase loginUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;

    @Value("${oauth.password}")
    private String oauthPassword;


    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO loginByBasic(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        return login(request.getEmail(), request.getPassword(), response);
    }

    @GetMapping("/api/auth/login/oauth")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO loginByOAuth2(@RequestParam("email") String email, HttpServletResponse response){
        return login(email, oauthPassword, response);
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
        return tokenReissueUseCase.reissue(request.getAccessToken(), request.getRefreshToken());
    }

}
