package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.service.UserWriteService;
import com.podo.pododev.web.global.config.security.SecurityStore;
import com.podo.pododev.web.global.config.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${security.oauth2.admin.ids}")
    private List<String> adminIds;

    @Value("${security.oauth2.redirect}")
    private String redirectUrl;

    private final UserWriteService userWriteService;
    private final SecurityStore securityStore;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {

        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
        final Map<String, String> details = (Map<String, String>) (oAuth2Authentication.getUserAuthentication().getDetails());
        final String googleId = details.get("sub");
        final String googleUsername = details.get("name");
        final String googleEmail = details.get("email");
        final String profileImage = details.get("picture");
        final String tokenByGoogle = ((OAuth2AuthenticationDetails) oAuth2Authentication.getDetails()).getTokenValue();
        final GoogleUserDetails googleUserDetails = GoogleUserDetails.builder()
                .googleId(googleId)
                .username(googleUsername)
                .email(googleEmail)
                .profileImage(profileImage)
                .build();


        userWriteService.writeUser(
                UserDto.insert.builder()
                        .userId(googleId)
                        .email(googleEmail)
                        .name(googleUsername)
                        .picture(profileImage)
                        .build()
        );

        googleUserDetails.setAuth(adminIds);

        securityStore.loginByToken(tokenByGoogle, new GoogleAuthentication(googleUserDetails));

        res.sendRedirect(redirectUrl + "?token=" + tokenByGoogle);
    }



}
