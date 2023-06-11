package refrigerator.back.authentication.infra.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refrigerator.back.authentication.application.port.out.CheckContainBlackListPort;
import refrigerator.back.authentication.infra.jwt.provider.JsonWebTokenProvider;
import refrigerator.back.authentication.infra.oauth.Oauth2FailureHandler;
import refrigerator.back.authentication.infra.oauth.Oauth2SuccessHandler;
import refrigerator.back.authentication.infra.oauth.PrincipalOAuth2DetailsService;
import refrigerator.back.authentication.infra.security.filter.JwtAuthenticationFilter;


import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final AuthenticationProvider authenticationProvider;
    private final CheckContainBlackListPort checkContainBlackListPort;
    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;
    
    @Value("${jwt.tokenPassword}")
    private String tokenPassword;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/test/**");
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        setOauth(http);
        setAuth(http);
        setComment(http);
        setIdentification(http);
        setMember(http);
        setRecipe(http);
        setWordCompletion(http);
        setDefault(http);
        setJwtFilter(http);
        return http.build();
    }
    private SessionManagementConfigurer<HttpSecurity> setDefault(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .mvcMatchers("/api/**").hasRole("STEADY_STATUS")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void setJwtFilter(HttpSecurity http) {
        http
                .addFilterBefore(
                        new JwtAuthenticationFilter(jsonWebTokenProvider, checkContainBlackListPort, tokenPassword),
                        UsernamePasswordAuthenticationFilter.class);
    }

    private void setWordCompletion(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/word-completion/recipe").permitAll()
                .mvcMatchers("/api/word-completion/ingredient").permitAll();
    }

    private void setRecipe(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/recipe/{recipeID}/course").permitAll()
                .mvcMatchers("/api/recipe").permitAll()
                .mvcMatchers("/api/recipe/{recipeId}/ingredient/volume").permitAll()
                .mvcMatchers("/api/recipe/search/normal").permitAll()
                .mvcMatchers("/api/recipe/search/condition/food-type").permitAll()
                .mvcMatchers("/api/recipe/search/condition/category").permitAll()
                .mvcMatchers("/api/recipe/search/condition/recipe-type").permitAll()
                .mvcMatchers("/api/recipe/search/condition/difficulty").permitAll();
    }

    private void setMember(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/members/join").permitAll()
                .mvcMatchers("/api/members/password/find").permitAll()
                .mvcMatchers("/api/members/password").permitAll()
                .mvcMatchers("/api/members/email/duplicate").permitAll()
                .mvcMatchers("/api/members/profile/list").permitAll();
    }

    private void setIdentification(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/identification/**").permitAll();
    }

    private void setComment(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/comments/heart/list").authenticated()
                .mvcMatchers("/api/comments/heart/**").permitAll()
                .mvcMatchers("/api/comments/date/**").permitAll()
                .mvcMatchers("/api/comments/preview/**").permitAll();
    }

    private void setAuth(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/auth/logout").authenticated()
                .mvcMatchers("/api/auth/**").permitAll();
    }

    private void setOauth(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2DetailsService)
                .and()
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailureHandler);
    }
}
