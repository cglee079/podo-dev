package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.web.domain.user.dto.UserInsert;
import com.podo.pododev.web.domain.user.value.UserVo;
import com.podo.pododev.web.domain.user.application.UserReadService;
import com.podo.pododev.web.domain.user.application.UserWriteService;
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
import java.util.Collections;

import static com.podo.pododev.web.global.config.security.role.UserRole.USER;

@RequiredArgsConstructor
@Service
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpServletResponse httpServletResponse;
    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private final SecurityStore securityStore;

    @Value("${security.login.success.url}")
    private String redirectUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();

        final OAuthAttributes attributes = getOAuthAttributes(oAuth2User, registrationId);

        final Long userId = userWriteService.writeUser(createUserInsertDto(attributes));
        final OAuthUserDetails oAuthUserDetails = createOAuthUserDetails(userId, attributes);

        final UserVo userVo = userReadService.getUser(userId);
        final String accessToken = securityStore.login(new OAuthAuthentication(oAuthUserDetails), userVo);

        httpServletResponse.setHeader(RequestHeader.ACCESS_TOKEN.value(), accessToken);

        return new DefaultOAuth2User(attributes.getAuthorities(), Collections.singletonMap("mock", "mock"), "mock");
    }

    private OAuthAttributes getOAuthAttributes(OAuth2User oAuth2User, String registrationId) {
        final OAuthAttributes attributes = OAuthType.from(registrationId).createAttributes(oAuth2User.getAttributes());
        attributes.setRole(userReadService.getRoleByKey(attributes.getUserKey()).orElse(USER));
        return attributes;
    }

    private UserInsert createUserInsertDto(OAuthAttributes attributes) {
        return UserInsert.builder()
                .oAuthType(attributes.getOAuthType())
                .userKey(attributes.getUserKey())
                .name(attributes.getUsername())
                .picture(attributes.getPicture())
                .role(attributes.getRole())
                .build();
    }

    private OAuthUserDetails createOAuthUserDetails(Long userId, OAuthAttributes attributes) {
        return OAuthUserDetails.builder()
                .userId(userId)
                .oAuthType(attributes.getOAuthType())
                .userKey(attributes.getUserKey())
                .username(attributes.getUsername())
                .picture(attributes.getPicture())
                .authorities(attributes.getAuthorities())
                .build();
    }
}
