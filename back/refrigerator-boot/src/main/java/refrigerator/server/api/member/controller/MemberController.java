package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.server.api.global.exception.ValidationExceptionHandler;
import refrigerator.server.api.member.dto.request.MemberNicknameUpdateRequestDTO;
import refrigerator.server.api.member.dto.request.MemberProfileUpdateRequestDTO;
import refrigerator.server.api.member.dto.request.MemberUpdatePasswordRequestDTO;
import refrigerator.server.api.member.dto.response.MemberDTO;
import refrigerator.server.api.member.dto.request.MemberInitNicknameAndProfileRequestDTO;
import refrigerator.back.member.application.dto.MemberProfileDto;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.exception.MemberExceptionType;


import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;
import static refrigerator.server.api.global.common.InputDataFormatCheck.*;

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
    public BasicListResponseDTO<MemberProfileDto> getProfileList(){
        return new BasicListResponseDTO<>(getProfileListUseCase.getProfileList());
    }

    @PutMapping("/api/members/init")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void initNicknameAndProfile(@RequestBody @Valid MemberInitNicknameAndProfileRequestDTO request, BindingResult result){
        ValidationExceptionHandler.check(result, MemberExceptionType.EMPTY_INPUT_DATA);

        inputCheck(NICKNAME_REGEX, request.getNickname(), MemberExceptionType.INCORRECT_NICKNAME_FORMAT);

        initNicknameAndProfileUseCase.initNicknameAndProfile(memberInformation.getMemberEmail(), request.getNickname(), request.getImageName());
    }
}
