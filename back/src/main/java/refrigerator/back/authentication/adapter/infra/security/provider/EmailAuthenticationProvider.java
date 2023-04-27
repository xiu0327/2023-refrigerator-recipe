package refrigerator.back.authentication.adapter.infra.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import refrigerator.back.authentication.adapter.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

@Component
@RequiredArgsConstructor
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenPassword}")
    private String tokenPassword;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(username);
        user.isEnabled();
        if (passwordEncoder.matches(password, user.getPassword()) || password.equals(tokenPassword)){
            return new EmailAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
                    );
        }
        throw new BusinessException(AuthenticationExceptionType.NOT_EQUAL_PASSWORD);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailAuthenticationToken.class);
    }
}