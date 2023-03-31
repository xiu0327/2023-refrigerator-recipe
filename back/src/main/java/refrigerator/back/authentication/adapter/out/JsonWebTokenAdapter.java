package refrigerator.back.authentication.adapter.out;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.port.out.FindEmailByToken;
import refrigerator.back.authentication.application.port.out.FindRefreshTokenByEmailPort;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;

import static refrigerator.back.authentication.adapter.infra.jwt.JsonWebTokenKey.*;

@Component
@RequiredArgsConstructor
public class JsonWebTokenAdapter implements CreateTokenPort, FindEmailByToken, FindRefreshTokenByEmailPort {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String createAccessToken(String username, String authority) {
        return jsonWebTokenProvider.createToken(
                username,
                authority,
                ACCESS_TOKEN_EXPIRE_TIME);
    }

    @Override
    public String createRefreshToken(String username, String authority) {
        String refreshToken = jsonWebTokenProvider.createToken(
                username,
                authority,
                REFRESH_TOKEN_EXPIRE_TIME);
        refreshTokenRepository.setData(username, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
        return refreshToken;
    }


    @Override
    public String findEmailByToken(String token) {
        Claims claims = jsonWebTokenProvider.parseClaims(token);
        return claims.getSubject();
    }

    @Override
    public String findRefreshToken(String email) {
        return refreshTokenRepository.getData(email);
    }
}
