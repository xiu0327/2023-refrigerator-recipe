package refrigerator.back.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.adapter.infra.oauth.Oauth2SuccessHandler;
import refrigerator.back.authentication.adapter.infra.oauth.PrincipalOAuth2DetailsService;
import refrigerator.back.authentication.adapter.infra.security.filter.JwtAuthenticationFilter;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final AuthenticationProvider authenticationProvider;
    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;

    @Value("${jwt.tokenPassword}")
    private String tokenPassword;

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/api/**");
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2DetailsService);
        http
                .oauth2Login()
                        .successHandler(oauth2SuccessHandler);
        http
                .authorizeRequests()
                .mvcMatchers("/api/members/join").permitAll()
                .mvcMatchers("/api/auth/**").permitAll()
                .mvcMatchers("/api/identification/**").permitAll()
                .mvcMatchers("/api/members/password/find").permitAll()
                .mvcMatchers("/api/members/email/duplicate").permitAll()
                .mvcMatchers("/api/members/profile/list").permitAll()
                .mvcMatchers("/api/word-completion/**").permitAll()
                .mvcMatchers("/api/recipe/search/condition/**").permitAll()
                .mvcMatchers("/oauth2/authorization/google").permitAll()
                .mvcMatchers("/api/**").hasRole("STEADY_STATUS")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jsonWebTokenProvider, tokenPassword), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
