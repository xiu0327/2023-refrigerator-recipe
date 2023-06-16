package refrigerator.back.authentication.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.adapter.out.repository.RefreshTokenRepository;
import refrigerator.back.authentication.application.domain.TokenInfoDTO;
import refrigerator.back.authentication.application.domain.TokenStatus;
import refrigerator.back.authentication.application.dto.ParseClaimsDto;
import refrigerator.back.authentication.application.port.external.JsonWebTokenProvider;
import refrigerator.back.authentication.application.port.out.*;



import java.util.Date;

import static refrigerator.back.authentication.adapter.out.JsonWebTokenKey.*;
import static refrigerator.back.authentication.application.domain.TokenStatus.*;


@Component
@RequiredArgsConstructor
public class JsonWebTokenAdapter implements CreateTokenPort, FindEmailByTokenPort,
        FindRefreshTokenByEmailPort, ValidateTokenPort, FindDurationByTokenPort {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final RefreshTokenRepository repository;

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
        repository.setData(username, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
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
    public TokenInfoDTO findEmailByToken(String token) {
        ParseClaimsDto parseClaimsDto = jsonWebTokenProvider.parseClaims(token);
        return new TokenInfoDTO(parseClaimsDto.getEmail(), parseClaimsDto.getRole());
    }

    @Override
    public String findRefreshToken(String email) {
        return repository.getData(email);
    }

    @Override
    public boolean validate(String accessToken, String refreshToken) {
        TokenStatus accessTokenStatus = jsonWebTokenProvider.validateToken(accessToken);
        TokenStatus refreshTokenStatus = jsonWebTokenProvider.validateToken(refreshToken);
        if (accessTokenStatus == TokenStatus.PASS && refreshTokenStatus == EXPIRED){
            // 액세스 토큰 = 유효, 리프레시 토큰 = 만료 -> 재발급 불가능
            return false;
        }
        // 액세스 토큰 = 만료, 리프레시 토큰 = 만료 -> 재발급 불가능, 그 이외 모든 경우 재발급 가능
        return !(accessTokenStatus == EXPIRED && refreshTokenStatus == EXPIRED);
    }

    @Override
    public boolean isExpired(String token) {
        TokenStatus tokenStatus = jsonWebTokenProvider.validateToken(token);
        return tokenStatus == EXPIRED;
    }

    @Override
    public Date findDuration(String token) {
        return jsonWebTokenProvider.parseClaims(token).getExpiration();
    }
}
