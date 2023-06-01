package refrigerator.back.authentication.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.back.authentication.adapter.in.dto.TokenDTO;
import refrigerator.back.authentication.application.port.in.IssueTemporaryAccessTokenUseCase;
import refrigerator.back.authentication.application.port.in.TokenReissueUseCase;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.common.CustomCookie;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.authentication.adapter.in.dto.IssueTemporaryAccessTokenRequestDTO;
import refrigerator.back.authentication.adapter.in.dto.TemporaryAccessTokenResponseDTO;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static refrigerator.back.global.exception.ValidationExceptionHandler.check;

@RestController
@Slf4j
public class TokenIssueController {

    private final TokenReissueUseCase tokenReissueUseCase;
    private final IssueTemporaryAccessTokenUseCase issueTemporaryAccessToken;
    private final CustomCookie refreshTokenCookie;
    private final String grantType;

    public TokenIssueController(TokenReissueUseCase tokenReissueUseCase,
                                IssueTemporaryAccessTokenUseCase issueTemporaryAccessToken,
                                @Qualifier("refreshTokenCookie") CustomCookie refreshTokenCookie,
                                @Value("${jwt.type}") String grantType) {
        this.tokenReissueUseCase = tokenReissueUseCase;
        this.issueTemporaryAccessToken = issueTemporaryAccessToken;
        this.refreshTokenCookie = refreshTokenCookie;
        this.grantType = grantType;
    }

    @PostMapping("/api/auth/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO reissue(HttpServletRequest request){
        String refreshToken = refreshTokenCookie.get(request.getCookies()).getValue();
        tokenReissueUseCase.reissue(refreshToken);
        throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_TOKEN);
    }

    @PostMapping("/api/auth/issue/temporary-token")
    public TemporaryAccessTokenResponseDTO findPassword(
            @RequestBody @Valid IssueTemporaryAccessTokenRequestDTO request,
            BindingResult result){
        check(result, MemberExceptionType.EMPTY_INPUT_DATA);
        String authToken = issueTemporaryAccessToken.issueTemporaryAccessToken(request.getEmail());
        return new TemporaryAccessTokenResponseDTO(grantType, authToken);
    }

}
