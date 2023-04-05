package refrigerator.back.authentication.adapter.infra.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.repository.MemberRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.exception.MemberExceptionType;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);});
        return User.builder()
                .username(member.getEmail())
                .authority(member.getMemberStatus().getStatusCode())
                .password(member.getPassword())
                .build();
    }
}
