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
public class AuthenticationController {

    private final TokenReissueUseCase tokenReissueUseCase;
    private final LogoutUseCase logoutUseCase;

    @GetMapping("/api/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response){
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        logoutUseCase.logout(accessToken);
        Cookie cookie = new Cookie("Refresh-Token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO reissue(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        try{
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Refresh-Token")){
                    return tokenReissueUseCase.reissue(cookie.getValue());
                }
            }
        }catch (NullPointerException e){
            throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_COOKIE);
        }
        throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_TOKEN);
    }

}
