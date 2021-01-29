package com.podo.pododev.web.global.util;

import com.podo.pododev.web.domain.user.exception.NoAuthenticatedApiException;
import com.podo.pododev.web.global.security.oauth.OAuthUserDetails;
import com.podo.pododev.web.global.security.role.UserRole;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
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

        if (Objects.isNull(authentication)){
            return Optional.empty();
        }

        final Object principal = authentication.getPrincipal();

        if (Objects.isNull(principal) || principal instanceof String) {
            return Optional.empty();
        }

        return Optional.of((OAuthUserDetails) principal);
    }

    public static void validateIsAuth() {
        if(!SecurityUtil.getUser().isPresent()){
            throw new NoAuthenticatedApiException();
        }
    }

    public static Long getUserId() {
        return SecurityUtil.getUser().map(OAuthUserDetails::getUserId).orElse(null);
    }

    public static String getUsername() {
        return SecurityUtil.getUser().map(OAuthUserDetails::getUsername).orElse(null);
    }

}
