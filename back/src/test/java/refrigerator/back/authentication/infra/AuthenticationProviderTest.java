package refrigerator.back.authentication.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

import javax.persistence.EntityManager;
import java.util.Set;

@SpringBootTest
@Transactional
public class AuthenticationProviderTest {

    @Autowired AuthenticationProvider provider;
    @Autowired EntityManager entityManager;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void 이메일_인증_공급자_테스트(){
        // given
        String rawPassword = "password1233!";
        Member member = Member.builder()
                .email("email123@gmail.com")
                .password(passwordEncoder.encode(rawPassword))
                .nickname("닉네임 뿅")
                .profile(MemberProfileImage.PROFILE_IMAGE_FIVE)
                .memberStatus(MemberStatus.STEADY_STATUS).build();
        entityManager.persist(member);
        // when
        /* 사용자가 클라이언트를 통해 email, password 입력 -> 인증 객체 변환 */
        Authentication authenticate = provider.authenticate(
                new EmailAuthenticationToken(
                        member.getEmail(),
                        rawPassword,
                        Set.of(new SimpleGrantedAuthority(MemberStatus.STEADY_STATUS.getStatusCode()))
                ));
        // then
        Assertions.assertThat(authenticate.getName()).isEqualTo(member.getEmail());
    }
}