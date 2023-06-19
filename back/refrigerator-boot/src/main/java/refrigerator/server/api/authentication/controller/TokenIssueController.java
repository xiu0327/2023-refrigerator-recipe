package refrigerator.server.api.authentication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.authentication.application.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.IssueTemporaryAccessTokenUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;
import refrigerator.server.api.global.common.CustomCookie;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.server.api.authentication.dto.TemporaryAccessTokenRequestDTO;
import refrigerator.server.api.authentication.dto.TemporaryAccessTokenResponseDTO;
import refrigerator.server.api.authentication.cookie.RefreshTokenCookie;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;


@RestController
@Slf4j
public class TokenIssueController {

    private final TokenReissueUseCase tokenReissueUseCase;
    private final IssueTemporaryAccessTokenUseCase issueTemporaryAccessToken;
    private final CustomCookie refreshTokenCookie = new RefreshTokenCookie();
    private final String grantType;

    public TokenIssueController(TokenReissueUseCase tokenReissueUseCase,
                                IssueTemporaryAccessTokenUseCase issueTemporaryAccessToken,
                                @Value("${jwt.type}") String grantType) {
        this.tokenReissueUseCase = tokenReissueUseCase;
        this.issueTemporaryAccessToken = issueTemporaryAccessToken;
        this.grantType = grantType;
    }

    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO reissue(HttpServletRequest request){
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        TokenDTO token = tokenReissueUseCase.reissue(refreshToken);
        token.removeRefreshToken();
        return token;
    }

    @PostMapping("/api/auth/issue/temporary-token")
    public TemporaryAccessTokenResponseDTO findPassword(
            @RequestBody @Valid TemporaryAccessTokenRequestDTO request,
            BindingResult result){
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        String authToken = issueTemporaryAccessToken.issueTemporaryAccessToken(request.getEmail());
        return new TemporaryAccessTokenResponseDTO(grantType, authToken);
    }

}
