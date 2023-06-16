package refrigerator.back.member.adapter.in.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.api.CustomCookie;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;


import javax.servlet.http.HttpServletResponse;

import static refrigerator.back.global.common.api.MemberInformation.getMemberEmail;

@RestController
public class WithdrawMemberController {

    private final WithdrawMemberUseCase withdrawMemberUseCase;
    private final CustomCookie refreshTokenCookie;

    public WithdrawMemberController(WithdrawMemberUseCase withdrawMemberUseCase,
                                    @Qualifier("refreshTokenCookie") CustomCookie refreshTokenCookie) {
        this.withdrawMemberUseCase = withdrawMemberUseCase;
        this.refreshTokenCookie = refreshTokenCookie;
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(HttpServletResponse response){
        withdrawMemberUseCase.withdrawMember(getMemberEmail());
        refreshTokenCookie.delete(response);
    }
}
