package refrigerator.back.authentication.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.port.out.AddBlackListTokenPort;
import refrigerator.back.authentication.application.port.out.CheckContainBlackListPort;
import refrigerator.back.authentication.application.port.out.RemoveRefreshTokenPort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenBlackListAdapter implements AddBlackListTokenPort, RemoveRefreshTokenPort, CheckContainBlackListPort {

    private final RefreshTokenRepository repository;

    @Override
    public void addToken(String token, long duration) {
        repository.setData(token, "logout", duration);
    }

    @Override
    public void removeRefreshToken(String email) {
        repository.removeData(email);
    }

    @Override
    public Boolean checkContainBlackList(String token) {
        return repository.getData(token) != null;
    }
}
