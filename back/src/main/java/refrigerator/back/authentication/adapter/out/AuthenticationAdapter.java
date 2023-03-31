package refrigerator.back.authentication.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.adapter.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.authentication.application.port.out.AuthenticatePort;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements EncryptPasswordPort, AuthenticatePort {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public String authenticate(String username, String password) {
        Authentication authentication = authenticationProvider.authenticate(EmailAuthenticationToken.builder()
                .username(username)
                .password(password)
                .build());
        return authentication.getAuthorities()
                .stream().map(Objects::toString)
                .findFirst()
                .orElseThrow(() -> new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY));
    }
}
