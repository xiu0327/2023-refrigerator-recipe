package refrigerator.back.member.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.member.adapter.dto.MemberNicknameUpdateRequestDTO;
import refrigerator.back.member.adapter.dto.MemberProfileUpdateRequestDTO;
import refrigerator.back.member.adapter.dto.MemberWithdrawRequestDTO;
import refrigerator.back.member.application.port.in.UpdateNicknameUseCase;
import refrigerator.back.member.application.port.in.UpdateProfileUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UpdateNicknameUseCase updateNicknameUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final WithdrawMemberUseCase withdrawMemberUseCase;


    @PutMapping("/api/members/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateNicknameUseCase(@RequestBody MemberNicknameUpdateRequestDTO request){
        /* 나중에 시큐리티 연결해서 email 불러올 예정 */
        String email = "";
        request.check();
        updateNicknameUseCase.updateNickname(email, request.getNewNickname());
    }

    @PutMapping("/api/members/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateProfileUseCase(@RequestBody MemberProfileUpdateRequestDTO request){
        /* 나중에 시큐리티 연결해서 email 불러올 예정 */
        String email = "";
        updateProfileUseCase.updateProfile(email, request.getNewProfileName());
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(@RequestBody MemberWithdrawRequestDTO request){
        /* 나중에 시큐리티 연결해서 email 불러올 예정 */
        String email = "";
        request.check();
        withdrawMemberUseCase.withdrawMember(email, request.getPassword());
    }
}
