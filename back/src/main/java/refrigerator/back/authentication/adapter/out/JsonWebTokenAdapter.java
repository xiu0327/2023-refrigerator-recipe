package refrigerator.back.authentication.adapter.out;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.adapter.infra.jwt.TokenStatus;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.port.out.FindEmailByToken;
import refrigerator.back.authentication.application.port.out.FindRefreshTokenByEmailPort;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.authentication.application.port.out.ValidateTokenPort;

import static refrigerator.back.authentication.adapter.infra.jwt.JsonWebTokenKey.*;
import static refrigerator.back.authentication.adapter.infra.jwt.TokenStatus.*;

@Component
@RequiredArgsConstructor
public class JsonWebTokenAdapter implements CreateTokenPort, FindEmailByToken,
        FindRefreshTokenByEmailPort, ValidateTokenPort {

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
    public String createTokenWithDuration(String username, String authority, long duration) {
        return jsonWebTokenProvider.createToken(
                username,
                authority,
                duration);
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

    @Override
    public boolean validate(String accessToken, String refreshToken) {
        TokenStatus accessTokenStatus = jsonWebTokenProvider.validateToken(accessToken);
        TokenStatus refreshTokenStatus = jsonWebTokenProvider.validateToken(refreshToken);
        if (accessTokenStatus == PASS && refreshTokenStatus == EXPIRED){
            // 액세스 토큰 = 유효, 리프레시 토큰 = 만료 -> 재발급 불가능
            return false;
        }
        // 액세스 토큰 = 만료, 리프레시 토큰 = 만료 -> 재발급 불가능, 그 이외 모든 경우 재발급 가능
        return !(accessTokenStatus == EXPIRED && refreshTokenStatus == EXPIRED);
    }
}
