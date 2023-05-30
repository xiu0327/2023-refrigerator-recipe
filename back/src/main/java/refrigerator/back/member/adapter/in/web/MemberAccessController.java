package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberJoinResponseDTO;
import refrigerator.back.member.adapter.infra.MemberCookieAdapter;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static refrigerator.back.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class MemberAccessController {

    private final JoinUseCase joinUseCase;
    private final DuplicateCheckEmailUseCase duplicateCheckEmailUseCase;

    @PostMapping("/api/members/join")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberJoinResponseDTO joinMember(
            @RequestBody @Valid MemberJoinRequestDTO requestDTO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response){
        MemberCookieAdapter cookieAdapter = new MemberCookieAdapter(request.getCookies());
        cookieAdapter.isVerified(response, requestDTO.getEmail());
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        requestDTO.check();
        Long memberID = joinUseCase.join(requestDTO.getEmail(), requestDTO.getPassword(), requestDTO.getNickname());
        return new MemberJoinResponseDTO(memberID);
    }

    @PostMapping("/api/members/email/duplicate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCheckEmail(@RequestBody @Valid MemberEmailParameterRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        duplicateCheckEmailUseCase.duplicateCheck(request.getEmail());
    }
}
