package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberNicknameUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberWithdrawRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberBasicDTO;
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
    private final CheckFirstLoginUseCase checkFirstLoginUseCase;


    @PutMapping("/api/members/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody @Valid MemberNicknameUpdateRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        updateNicknameUseCase.updateNickname(getMemberEmail(), request.getNickname());
    }

    @PutMapping("/api/members/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestParam("imageName") String imageName){
        updateProfileUseCase.updateProfile(getMemberEmail(), imageName);
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(@RequestBody @Valid MemberWithdrawRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.NOT_EMPTY_INPUT_DATA);
        withdrawMemberUseCase.withdrawMember(getMemberEmail());
    }

    @GetMapping("/api/members")
    public MemberDTO findMember(){
        Member member = findMemberInfoUseCase.findMember(getMemberEmail());
        return new MemberDTO(
                makeProfileUrlUseCase.createURL(member.getProfile().getName()),
                member.getNickname());
    }

    @GetMapping("/api/members/profile/list")
    public BasicListResponseDTO<MemberProfileDTO> getProfileList(){
        return new BasicListResponseDTO<>(getProfileListUseCase.getProfileList());
    }

    @GetMapping("/api/members/check/first-login")
    public MemberBasicDTO<Boolean> isFirstLoginMember(){
        return new MemberBasicDTO<>(checkFirstLoginUseCase.checkFirstLogin(getMemberEmail()));
    }
}
