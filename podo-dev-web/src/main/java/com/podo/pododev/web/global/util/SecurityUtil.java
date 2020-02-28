package com.podo.pododev.web.global.util;

import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import com.podo.pododev.web.global.config.security.role.UserRole;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public class SecurityUtil extends WebSecurityConfigurerAdapter {

    public static Boolean isAdmin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        @SuppressWarnings("unchecked")
        final List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        for (SimpleGrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals("ROLE_" + UserRole.ADMIN)) {
                return true;
            }
        }

        return false;
    }


    public static Optional<OAuthUserDetails> getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            return Optional.empty();
        }

        return Optional.of((OAuthUserDetails) principal);
    }

    public static String getUserKey() {
        return SecurityUtil.getUser().map(OAuthUserDetails::getUserKey).orElse(null);
    }

    public static String getUsername() {
        return SecurityUtil.getUser().map(OAuthUserDetails::getUsername).orElse(null);
    }

}
