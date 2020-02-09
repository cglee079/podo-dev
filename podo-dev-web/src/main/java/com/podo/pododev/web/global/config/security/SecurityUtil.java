package com.podo.pododev.web.global.config.security;

import com.podo.pododev.web.global.config.security.oauth.GoogleUserDetails;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtil extends WebSecurityConfigurerAdapter {

    public static Boolean isAdmin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        for (SimpleGrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals("ROLE_" + UserRole.ADMIN)) {
                return true;
            }
        }

        return false;
    }


    public static GoogleUserDetails getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            return null;
        }

        return (GoogleUserDetails) principal;
    }

    public static String getUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            return null;
        }

        return ((GoogleUserDetails) principal).getGoogleId();
    }

    public static String getUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            return null;
        }

        return ((GoogleUserDetails) principal).getUsername();
    }

}
