package refrigerator.back.authentication.adapter.in.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.authentication.application.port.in.LogoutUseCase;
import refrigerator.back.global.common.CustomCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {
    private final LogoutUseCase logoutUseCase;
    private final CustomCookie refreshTokenCookie;

    public LogoutController(LogoutUseCase logoutUseCase,
                            @Qualifier("refreshTokenCookie") CustomCookie refreshTokenCookie) {
        this.logoutUseCase = logoutUseCase;
        this.refreshTokenCookie = refreshTokenCookie;
    }

    @GetMapping("/api/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response){
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        logoutUseCase.logout(accessToken);
        refreshTokenCookie.delete(response);
    }
}
