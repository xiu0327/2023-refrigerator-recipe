package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.global.exception.ValidationExceptionHandler;
import refrigerator.back.member.adapter.in.dto.request.*;
import refrigerator.back.member.adapter.in.dto.response.MemberDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.Valid;

import static refrigerator.back.global.common.MemberInformation.*;
import static refrigerator.back.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final WithdrawMemberUseCase withdrawMemberUseCase;
    private final MakeProfileUrlUseCase makeProfileUrlUseCase;
    private final FindMemberInfoUseCase findMemberInfoUseCase;
    private final GetProfileListUseCase getProfileListUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final InitNicknameAndProfileUseCase initNicknameAndProfileUseCase;


    @PutMapping("/api/members/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody @Valid MemberNicknameUpdateRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        updateNicknameUseCase.updateNickname(getMemberEmail(), request.getNickname());
    }

    @PutMapping("/api/members/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestBody @Valid MemberProfileUpdateRequestDTO request, BindingResult result){
        ValidationExceptionHandler.check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        updateProfileUseCase.updateProfile(getMemberEmail(), request.getImageName());
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(){
        withdrawMemberUseCase.withdrawMember(getMemberEmail());
    }

    @GetMapping("/api/members")
    public MemberDTO findMember(){
        Member member = findMemberInfoUseCase.findMember(getMemberEmail());
        return new MemberDTO(
                makeProfileUrlUseCase.createURL(member.getProfile().getName()),
                member.getNickname());
    }

    @PutMapping("/api/members/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid MemberUpdatePasswordRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        updatePasswordUseCase.updatePassword(
                getMemberEmail(),
                request.getPassword());
    }

    @GetMapping("/api/members/profile/list")
    public BasicListResponseDTO<MemberProfileDTO> getProfileList(){
        return new BasicListResponseDTO<>(getProfileListUseCase.getProfileList());
    }

    @PutMapping("/api/members/init")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void initNicknameAndProfile(@RequestBody @Valid MemberInitNicknameAndProfileRequestDTO request, BindingResult result){
        ValidationExceptionHandler.check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        initNicknameAndProfileUseCase.initNicknameAndProfile(getMemberEmail(), request.getNickname(), request.getImageName());
    }
}
