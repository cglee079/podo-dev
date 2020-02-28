package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.service.UserWriteService;
import com.podo.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public static final String ACCESS_TOKEN_OF_HEADER = "Access-Token";

    private final HttpServletResponse httpServletResponse;
    private final UserWriteService userWriteService;
    private final SecurityStore securityStore;

    @Value("${security.admin.keys}")
    private List<String> adminUserKeys;

    @Value("${security.login.success.url}")
    private String redirectUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        final OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes(), adminUserKeys);

        userWriteService.writeUser(createUserInsertDto(attributes));
        final OAuthUserDetails oAuthUserDetails = createOAuthUserDetails(attributes);

        final String accessToken = securityStore.login(new OAuthAuthentication(oAuthUserDetails));

        httpServletResponse.setHeader(ACCESS_TOKEN_OF_HEADER, accessToken);

        return new DefaultOAuth2User(attributes.getAuthorities(), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private OAuthUserDetails createOAuthUserDetails(OAuthAttributes attributes) {
        return OAuthUserDetails.builder()
                .userKey(attributes.getUserKey())
                .username(attributes.getName())
                .profileImage(attributes.getPicture())
                .authorities(attributes.getAuthorities())
                .build();
    }

    private UserDto.insert createUserInsertDto(OAuthAttributes attributes) {
        return UserDto.insert.builder()
                .userId(attributes.getUserKey())
                .name(attributes.getName())
                .picture(attributes.getPicture())
                .build();
    }
}
