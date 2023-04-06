package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberFindPasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberJoinRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberUpdatePasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberFindPasswordResponseDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberJoinResponseDTO;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;

import static refrigerator.back.global.common.MemberInformation.*;

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
    public MemberJoinResponseDTO joinMember(@RequestBody MemberJoinRequestDTO request){
        request.check();
        Long memberID = joinUseCase.join(
                request.getEmail(),
                request.getPassword(),
                request.getNickname());
        return new MemberJoinResponseDTO(memberID);
    }

    @PostMapping("/api/members/password/find")
    public MemberFindPasswordResponseDTO findPassword(@RequestBody MemberFindPasswordRequestDTO request){
        request.check();
        String authToken = findPasswordUseCase.findPassword(request.getEmail());
        return new MemberFindPasswordResponseDTO(grantType, authToken);
    }

    @PutMapping("/api/members/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody MemberUpdatePasswordRequestDTO request){
        request.check();
        findPasswordUseCase.updatePassword(
                getMemberEmail(),
                request.getPassword());
    }

    @GetMapping("/api/members/email/duplicate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCheckEmail(@RequestBody MemberEmailParameterRequestDTO request){
        request.check();
        duplicateCheckEmailUseCase.duplicateCheck(request.getEmail());
    }

}
