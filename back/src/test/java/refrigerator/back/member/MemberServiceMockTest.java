package refrigerator.back.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.service.MemberService;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {

    @InjectMocks MemberService memberService;
    @Mock FindMemberPort findMemberPort;

    @Test
    @DisplayName("캐시 회원 찾기")
    void findMemberUseCache(){
        String email = "nhtest@gmail.com";
        Member member = Member.join(
                email,
                "password123!",
                "닉네임");
        given(findMemberPort.findMember(email))
                .willReturn(member);
        Member findMember = memberService.findMember(email);
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}
