package refrigerator.back.authentication.adapter.out.repository;

import org.springframework.data.repository.CrudRepository;
import refrigerator.back.authentication.application.dto.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
