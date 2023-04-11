package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.global.common.BasicListResponseDTO;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.global.common.MemberInformation;
import refrigerator.back.member.adapter.in.dto.request.MemberEmailParameterRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberNicknameUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberProfileUpdateRequestDTO;
import refrigerator.back.member.adapter.in.dto.request.MemberWithdrawRequestDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberDTO;
import refrigerator.back.member.adapter.in.dto.response.MemberProfileDTO;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.*;

import static refrigerator.back.global.common.MemberInformation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final WithdrawMemberUseCase withdrawMemberUseCase;
    private final MakeProfileUrlUseCase makeProfileUrlUseCase;
    private final FindMemberInfoUseCase findMemberInfoUseCase;
    private final GetProfileListUseCase getProfileListUseCase;


    @PutMapping("/api/members/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody MemberNicknameUpdateRequestDTO request){
        request.check();
        updateNicknameUseCase.updateNickname(getMemberEmail(), request.getNickname());
    }

    @PutMapping("/api/members/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestParam("imageName") String imageName){
        updateProfileUseCase.updateProfile(getMemberEmail(), imageName);
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(@RequestBody MemberWithdrawRequestDTO request){
        request.check();
        withdrawMemberUseCase.withdrawMember(getMemberEmail(), request.getPassword());
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
}
