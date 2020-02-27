package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.service.UserWriteService;
import com.podo.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpServletResponse httpServletResponse;
    private final UserWriteService userWriteService;
    private final SecurityStore securityStore;

    @Value("${security.admin.ids}")
    private List<String> adminIds;

    @Value("${security.login.success.url}")
    private String redirectUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        final OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        userWriteService.writeUser(createUserInsertDto(attributes));
        final OAuthUserDetails oAuthUserDetails = createOAuthUserDetails(attributes, adminIds);

        final String accessToken = securityStore.login(new OAuthAuthentication(oAuthUserDetails));

        httpServletResponse.setHeader("Access-Token", accessToken);

        return new DefaultOAuth2User(oAuthUserDetails.getAuthorities(), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private OAuthUserDetails createOAuthUserDetails(OAuthAttributes attributes, List<String> adminIds) {
        return OAuthUserDetails.builder()
                .userKey(attributes.getUserKey())
                .username(attributes.getName())
                .profileImage(attributes.getPicture())
                .adminIds(adminIds)
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
