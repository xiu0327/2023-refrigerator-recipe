package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.member.adapter.in.web.dto.*;
import refrigerator.back.member.application.port.in.DuplicateCheckEmailUseCase;
import refrigerator.back.member.application.port.in.FindPasswordUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;

@RestController
@RequiredArgsConstructor
public class MemberAccessController {

    private final JoinUseCase joinUseCase;
    private final FindPasswordUseCase findPasswordUseCase;
    private final DuplicateCheckEmailUseCase duplicateCheckEmailUseCase;


    @PostMapping("/api/members")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberJoinResponseDTO joinMember(@RequestBody MemberJoinRequestDTO request){
        request.check();
        Long memberID = joinUseCase.join(request.getEmail(), request.getPassword(), request.getNickname());
        return new MemberJoinResponseDTO(memberID);
    }

    @PostMapping("/api/members/password/find")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findPassword(@RequestBody MemberFindPasswordRequestDTO request){
        request.check();
        findPasswordUseCase.findPassword(request.getEmail());
    }

    @PutMapping("/api/members/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody MemberUpdatePasswordRequestDTO request){
        request.check();
        findPasswordUseCase.updatePassword(request.getEmail(), request.getPassword());
    }

    @GetMapping("/api/member/email/duplicate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCheckEmail(@RequestBody MemberDuplicateCheckEmailRequestDTO request){
        request.check();
        duplicateCheckEmailUseCase.duplicateCheck(request.getEmail());
    }

}
