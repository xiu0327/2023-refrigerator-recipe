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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refrigerator.back.authentication.adapter.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.adapter.infra.security.filter.JwtAuthenticationFilter;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final AuthenticationProvider authenticationProvider;

    @Value("${jwt.tokenPassword}")
    private String tokenPassword;

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .mvcMatchers("/api/members/join").permitAll()
                .mvcMatchers("/api/auth/login").permitAll()
                .mvcMatchers("/api/auth/reissue").permitAll()
                .mvcMatchers("/api/identification/**").permitAll()
                .mvcMatchers("/api/members/password/find").permitAll()
                .mvcMatchers("/api/members/email/duplicate").permitAll()
                .mvcMatchers("/test").permitAll()
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
