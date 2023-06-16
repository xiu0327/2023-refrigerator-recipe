package refrigerator.back.authentication.infra.security.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import refrigerator.back.authentication.application.port.out.CheckContainBlackListPort;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.authentication.exception.JwtExceptionType;
import refrigerator.back.authentication.infra.jwt.JsonWebTokenKey;
import refrigerator.back.authentication.infra.jwt.TokenStatus;
import refrigerator.back.authentication.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.infra.security.handler.MakeResponseMessageHandler;
import refrigerator.back.authentication.infra.security.token.EmailAuthenticationToken;
import refrigerator.back.global.exception.domain.BusinessException;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static org.springframework.util.StringUtils.hasText;
import static refrigerator.back.authentication.infra.jwt.JsonWebTokenKey.AUTHORITIES_KEY;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilter {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final CheckContainBlackListPort checkContainBlackListPort;
    private final String tokenPassword;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);
        try{
            if (token != null && checkContainBlackListPort.checkContainBlackList(token)){
                throw new BusinessException(AuthenticationExceptionType.ALREADY_LOGOUT_MEMBER);
            }
            if (token != null && checkToken(token)){
                Claims claims = jsonWebTokenProvider.parseClaims(token);
                if (claims.get(AUTHORITIES_KEY) == null || !hasText(claims.get(AUTHORITIES_KEY).toString())){
                    throw new BusinessException(AuthenticationExceptionType.NOT_FOUND_AUTHORITY);
                }
                Authentication authentication = new EmailAuthenticationToken(
                        claims.getSubject(),
                        tokenPassword,
                        Set.of(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY).toString())));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }catch (BusinessException e){
            MakeResponseMessageHandler.makeResponseMessage((HttpServletResponse) response, e);
        }
    }

    private boolean checkToken(String token){
        TokenStatus tokenStatus = jsonWebTokenProvider.validateToken(token);
        if (tokenStatus == TokenStatus.PASS){
            return true;
        }
        if (tokenStatus == TokenStatus.EXPIRED){
            throw new BusinessException(JwtExceptionType.ACCESS_TOKEN_EXPIRED);
        }
        if (tokenStatus == TokenStatus.WRONG){
            throw new BusinessException(JwtExceptionType.INVALID_ACCESS_TOKEN);
        }
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(JsonWebTokenKey.BEARER_TYPE + " ")){
            return token.substring(7);
        }
        return null;
    }

}
