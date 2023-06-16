package refrigerator.server.security.security.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.application.port.out.CreateAuthenticationPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.security.security.token.EmailAuthenticationToken;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateAuthenticationAdapter implements CreateAuthenticationPort {

    private final AuthenticationProvider authenticationProvider;

    @Override
    public String authenticate(String username, String password) {
        Authentication authentication = authenticationProvider.authenticate(
                new EmailAuthenticationToken(username, password));
        return authentication.getAuthorities()
                .stream().map(Objects::toString)
                .findFirst()
                .orElseThrow(() -> new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY));
    }
}
