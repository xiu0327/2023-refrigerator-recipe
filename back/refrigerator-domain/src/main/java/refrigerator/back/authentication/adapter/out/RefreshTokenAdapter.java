package refrigerator.back.authentication.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.dto.RefreshToken;
import refrigerator.back.authentication.application.port.out.*;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenPort{

    private final RefreshTokenRepository repository;

    @Override
    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> getToken(String token) {
        return repository.findRefreshTokenByToken(token);
    }
}
