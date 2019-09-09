package com.cglee079.pododev.web.global.config.security.oauth;

import com.cglee079.pododev.web.global.config.security.SecurityStore;
import com.cglee079.pododev.web.global.config.security.UserRole;
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
    List<String> adminIds;

    @Value("${security.oauth2.redirect}")
    String redirectUrl;


    private final SecurityStore securityStore;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {

        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
        final Map<String, String> details = (Map<String, String>) (oAuth2Authentication.getUserAuthentication().getDetails());
        final String googleId = details.get("sub");
        final String username = details.get("name");
        final String email = details.get("email");
        final String picture = details.get("picture");
        final String token = ((OAuth2AuthenticationDetails) oAuth2Authentication.getDetails()).getTokenValue();
        final GoogleUserDetails googleUserDetails = GoogleUserDetails.builder()
                .googleIdentifier(googleId)
                .username(username)
                .email(email)
                .picture(picture)
                .build();

        googleUserDetails.addAuthority(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));

        if (adminIds.contains(googleId)) {
            googleUserDetails.addAuthority(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN));
        }

        GoogleAuthentication googleAuthentication = new GoogleAuthentication(googleUserDetails);

        securityStore.login(token, googleAuthentication);

        //Go Home
        res.sendRedirect(redirectUrl + "?token=" + token);
    }

}
