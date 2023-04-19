package refrigerator.back.authentication.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.member.application.domain.MemberStatus;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {

    @Autowired UserDetailsService service;
    @Autowired TestData testData;

    @Test
    void 사용자_정보_서비스_테스트(){
        String username = testData.createMemberByEmail("email123@gmail.com");
        String authorityCode = MemberStatus.STEADY_STATUS.getStatusCode();
        UserDetails user = service.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
        for (GrantedAuthority authority : user.getAuthorities()) {
            assertThat(authority.getAuthority()).isEqualTo(authorityCode);
        }
    }

}