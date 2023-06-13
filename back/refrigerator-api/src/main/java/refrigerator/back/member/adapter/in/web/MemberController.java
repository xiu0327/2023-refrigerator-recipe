package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.global.exception.ValidationExceptionHandler;
import refrigerator.back.member.adapter.in.dto.request.MemberInitNicknameAndProfileRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberNicknameUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberProfileUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberUpdatePasswordRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.exception.MemberExceptionType;


import javax.validation.Valid;

import static refrigerator.back.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final MakeProfileUrlUseCase makeProfileUrlUseCase;
    private final FindMemberInfoUseCase findMemberInfoUseCase;
    private final GetProfileListUseCase getProfileListUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final InitNicknameAndProfileUseCase initNicknameAndProfileUseCase;
    private final GetMemberEmailUseCase memberInformation;


    @PutMapping("/api/members/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody @Valid MemberNicknameUpdateRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
        updateNicknameUseCase.updateNickname(memberInformation.getMemberEmail(), request.getNickname());
    }

    @PutMapping("/api/members/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestBody @Valid MemberProfileUpdateRequestDTO request, BindingResult result){
        ValidationExceptionHandler.check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        updateProfileUseCase.updateProfile(memberInformation.getMemberEmail(), request.getImageName());
    }

    @GetMapping("/api/members")
    public MemberDTO findMember(){
        Member member = findMemberInfoUseCase.findMember(memberInformation.getMemberEmail());
        return new MemberDTO(
                makeProfileUrlUseCase.createURL(member.getProfile().getName()),
                member.getNickname());
    }

    @PutMapping("/api/members/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid MemberUpdatePasswordRequestDTO request, BindingResult result){
        check(result, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        updatePasswordUseCase.updatePassword(
                memberInformation.getMemberEmail(),
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
        initNicknameAndProfileUseCase.initNicknameAndProfile(memberInformation.getMemberEmail(), request.getNickname(), request.getImageName());
    }
}
