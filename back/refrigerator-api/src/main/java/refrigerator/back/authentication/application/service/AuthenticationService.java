package refrigerator.back.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.domain.TokenInfoDTO;
import refrigerator.back.authentication.application.port.in.LoginUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;
import refrigerator.back.authentication.application.port.out.CreateAuthenticationPort;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.authentication.application.port.out.FindEmailByTokenPort;
import refrigerator.back.authentication.application.port.out.ValidateTokenPort;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.global.exception.BusinessException;

import static refrigerator.back.authentication.infra.jwt.JsonWebTokenKey.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService implements LoginUseCase, TokenReissueUseCase {

    private final CreateTokenPort createTokenPort;
    private final CreateAuthenticationPort authenticatePort;
    private final FindEmailByTokenPort findEmailByToken;
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
    public TokenDTO reissue(String refreshToken) {
        // TODO : refreshToken 이 블랙 리스트에 있는 토큰인지 확인하는 코드 추가
        if (validateTokenPort.isExpired(refreshToken)){
            throw new BusinessException(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
        }
        TokenInfoDTO tokenInfo = findEmailByToken.findEmailByToken(refreshToken);
        String email = tokenInfo.getEmail();
        String authority = tokenInfo.getRole();
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(createTokenPort.createAccessToken(email, authority))
                .build();
    }

}
