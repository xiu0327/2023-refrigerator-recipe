package refrigerator.back.authentication.infra.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.FindMemberInfoUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrincipalOAuth2DetailsService extends DefaultOAuth2UserService {

    private final FindMemberInfoUseCase findMemberInfoUseCase;
    private final JoinUseCase joinUseCase;
    @Value("${oauth.password}")
    private String oauthPassword;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String providerId = userRequest.getClientRegistration().getRegistrationId();
        String email = getEmail(providerId, oAuth2User);
        String nickname = getNickname(providerId, oAuth2User);
        Optional<Member> member = Optional.ofNullable(findMemberInfoUseCase.findMemberNotUseCache(email));
        if (member.isPresent()){
            return OauthUser.builder()
                    .username(email)
                    .password(oauthPassword)
                    .attributes(oAuth2User.getAttributes())
                    .authority(member.get().getMemberStatus().getStatusCode()).build();
        }
        joinUseCase.join(email, oauthPassword, nickname);
        return OauthUser.builder()
                .username(email)
                .password(oauthPassword)
                .attributes(oAuth2User.getAttributes())
                .authority(MemberStatus.STEADY_STATUS.getStatusCode()).build();
    }

    private String getNickname(String providerId, OAuth2User oAuth2User){
        if (providerId.equals("google")){
            return oAuth2User.getAttribute("name");
        }
        if (providerId.equals("naver")){
            Map<String, String> result = oAuth2User.getAttribute("response");
            return result.get("nickname");
        }
        throw new OAuth2AuthenticationException(
                new OAuth2Error("NOT_FOUND_OAUTH2_NICKNAME", "회원의 정보를 가져오지 못했습니다", "")
        );
    }

    private String getEmail(String providerId, OAuth2User oAuth2User) {
        if (providerId.equals("google")){
            return oAuth2User.getAttribute("email");
        }
        if (providerId.equals("naver")){
            Map<String, String> result = oAuth2User.getAttribute("response");
            return result.get("email");
        }
        throw new OAuth2AuthenticationException(
                new OAuth2Error("NOT_FOUND_OAUTH2_EMAIL", "회원의 정보를 가져오지 못했습니다", "")
        );
    }
}
