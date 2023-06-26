package refrigerator.server.security.authentication.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;

@Component
@RequiredArgsConstructor
public class EncryptPasswordAdapter implements EncryptPasswordPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

}
