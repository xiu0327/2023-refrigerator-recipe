package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.MemberEmailCheckRequestDto;
import refrigerator.back.member.application.port.in.EmailDuplicationCheckUseCase;
import refrigerator.back.member.exception.MemberExceptionType;


import javax.servlet.http.HttpServletResponse;

import static refrigerator.server.api.global.common.InputDataFormatCheck.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberAccessController {
    private final EmailDuplicationCheckUseCase duplicateCheckEmailUseCase;
    private final MemberEmailCheckCookieAdapter cookieAdapter;

    @PostMapping("/api/members/email/duplicate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCheckEmail(@RequestBody MemberEmailCheckRequestDto request,
                                    HttpServletResponse response){
        inputCheck(
                EMAIL_REGEX,
                request.getEmail(),
                MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        duplicateCheckEmailUseCase.duplicateCheck(request.getEmail());
        response.addCookie(cookieAdapter.create());
    }
}
