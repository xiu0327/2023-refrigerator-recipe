package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.exception.ValidationExceptionHandler;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberFindPasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberUpdatePasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberFindPasswordResponseDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberJoinResponseDTO;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.Valid;

import static refrigerator.back.global.common.MemberInformation.*;
import static refrigerator.back.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class MemberAccessController {

    private final JoinUseCase joinUseCase;
    private final FindPasswordUseCase findPasswordUseCase;
    private final DuplicateCheckEmailUseCase duplicateCheckEmailUseCase;
    @Value("${jwt.type}")
    private String grantType;

    @PostMapping("/api/members/join")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberJoinResponseDTO joinMember(@RequestBody @Valid MemberJoinRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        request.check();
        Long memberID = joinUseCase.join(
                request.getEmail(),
                request.getPassword(),
                "");
        return new MemberJoinResponseDTO(memberID);
    }

    @PostMapping("/api/members/password/find")
    public MemberFindPasswordResponseDTO findPassword(@RequestBody @Valid MemberFindPasswordRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        String authToken = findPasswordUseCase.findPassword(request.getEmail());
        return new MemberFindPasswordResponseDTO(grantType, authToken);
    }

    @PutMapping("/api/members/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid MemberUpdatePasswordRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        findPasswordUseCase.updatePassword(
                getMemberEmail(),
                request.getPassword());
    }

    @PostMapping("/api/members/email/duplicate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCheckEmail(@RequestBody @Valid MemberEmailParameterRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        duplicateCheckEmailUseCase.duplicateCheck(request.getEmail());
    }
}
