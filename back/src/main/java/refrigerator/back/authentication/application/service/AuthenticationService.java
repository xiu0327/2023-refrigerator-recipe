package refrigerator.back.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;
import refrigerator.back.authentication.application.port.out.*;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.port.out.FindMemberPort;

import static refrigerator.back.authentication.adapter.infra.jwt.JsonWebTokenKey.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService implements LoginUseCase, TokenReissueUseCase {

    private final CreateTokenPort createTokenPort;
    private final AuthenticatePort authenticatePort;
    private final FindEmailByToken findEmailByToken;
    private final FindRefreshTokenByEmailPort findRefreshTokenByEmailPort;
    private final FindMemberPort findMemberPort;
    private final ValidateTokenPort validateTokenPort;

    @Override
    public TokenDTO login(String email, String password) {
        String authority = authenticatePort.authenticate(email, password);
        String accessToken = createTokenPort.createAccessToken(email, authority);
        String refreshToken = createTokenPort.createRefreshToken(email, authority);
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenDTO reissue(String accessToken, String refreshToken) {
        checkToken(accessToken, refreshToken);
        String email = findEmailByToken.findEmailByToken(refreshToken);
        String authority = findMemberPort.findMember(email).getMemberStatus().getStatusCode();
        String findRefreshToken = findRefreshTokenByEmailPort.findRefreshToken(email);
        if (!refreshToken.equals(findRefreshToken)){
            throw new BusinessException(JwtExceptionType.INVALID_REFRESH_TOKEN);
        }
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(createTokenPort.createAccessToken(email, authority))
                .refreshToken(findRefreshToken)
                .build();
    }

    private void checkToken(String accessToken, String refreshToken) {
        if (!validateTokenPort.validate(accessToken, refreshToken)){
            throw new BusinessException(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
        }
    }
}
