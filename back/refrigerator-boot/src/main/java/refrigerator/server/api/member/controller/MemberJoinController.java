package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.member.cookie.MemberEmailCheckCookieAdapter;
import refrigerator.server.api.member.dto.request.MemberJoinRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberJoinController {

    private final JoinUseCase joinUseCase;
    private final PasswordEncoder passwordEncoder;
    private final MemberEmailCheckCookieAdapter cookieAdapter;

    @PostMapping("/api/members/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinMember(
            @RequestBody MemberJoinRequestDto requestDto,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse){
        if (!cookieAdapter.isExist(httpServletRequest.getCookies())){
            throw new BusinessException(MemberExceptionType.NOT_COMPLETED_EMAIL_DUPLICATION_CHECK);
        }
        requestDto.check();
        Long memberId = joinUseCase.join(
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname());
        httpServletResponse.addCookie(cookieAdapter.delete());
    }
}
