package refrigerator.back.authentication.adapter.in.web;

import lombok.RequiredArgsConstructor;
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
public class AuthenticationController {

    private final LoginUseCase loginUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;


    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO login(@RequestBody LoginRequestDTO request, HttpServletResponse response){
        TokenDTO token = loginUseCase.login(request.getEmail(), request.getPassword());
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
