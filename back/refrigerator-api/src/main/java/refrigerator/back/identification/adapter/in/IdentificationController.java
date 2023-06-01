package refrigerator.back.identification.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.identification.adapter.dto.CheckNumberRequestDTO;
import refrigerator.back.identification.adapter.dto.SendNumberRequestDTO;
import refrigerator.back.identification.application.port.in.CheckNumberUseCase;
import refrigerator.back.identification.application.port.in.SendNumberUseCase;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class IdentificationController {

    private final CheckNumberUseCase checkNumberUseCase;
    private final SendNumberUseCase sendNumberUseCase;

    @PostMapping("/api/identification/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void send(@RequestBody SendNumberRequestDTO request){
        sendNumberUseCase.sendAuthenticationNumber(
                request.getEmail(),
                Integer.valueOf(1000 * 60 * 30).longValue());
    }

    @PostMapping("/api/identification/check")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkNumber(@RequestBody CheckNumberRequestDTO request, HttpServletResponse response){
        checkNumberUseCase.checkAuthenticationNumber(request.getInputCode(), request.getEmail());
        Cookie cookie = new Cookie("Verified-User", request.getEmail());
        cookie.setHttpOnly(true);
        cookie.setPath("/api/members/join");
        cookie.setMaxAge(3 * 60);
        response.addCookie(cookie);
    }
}
