package refrigerator.back.member.adapter.in.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.global.common.CustomCookie;
import refrigerator.back.authentication.application.port.in.GetMemberEmailUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;


import javax.servlet.http.HttpServletResponse;


@RestController
public class WithdrawMemberController {

    private final WithdrawMemberUseCase withdrawMemberUseCase;
    private final CustomCookie refreshTokenCookie;
    private final GetMemberEmailUseCase memberInformation;

    public WithdrawMemberController(WithdrawMemberUseCase withdrawMemberUseCase,
                                    GetMemberEmailUseCase memberInformation,
                                    @Qualifier("refreshTokenCookie") CustomCookie refreshTokenCookie) {
        this.withdrawMemberUseCase = withdrawMemberUseCase;
        this.refreshTokenCookie = refreshTokenCookie;
        this.memberInformation = memberInformation;
    }

    @DeleteMapping("/api/members")
    public void setWithdrawMemberUseCase(HttpServletResponse response){
        withdrawMemberUseCase.withdrawMember(memberInformation.getMemberEmail());
        refreshTokenCookie.delete(response);
    }
}
