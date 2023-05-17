package refrigerator.back.authentication.adapter.infra.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.member.application.port.in.CheckFirstLoginUseCase;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CheckFirstLoginAuthenticationFilter extends OncePerRequestFilter {

    private final CheckFirstLoginUseCase checkFirstLoginUseCase;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final String frontDomain;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String initStatus = request.getHeader("Init-Status");
            if (initStatus == null){
                String header = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (header != null){
                    String accessToken = header.split(" ")[1];
                    String email = jsonWebTokenProvider.parseClaims(accessToken).getSubject();
                    if (checkFirstLoginUseCase.checkFirstLogin(email)){
                        response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                        response.sendRedirect(frontDomain + "/member/profile");
                        return;
                    }
                }
            }
        } catch(AuthenticationException e){
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
