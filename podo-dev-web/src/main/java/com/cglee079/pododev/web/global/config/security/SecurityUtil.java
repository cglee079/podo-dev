package com.cglee079.pododev.web.global.config.security;

import com.cglee079.pododev.web.domain.auth.exception.NoAuthenticatedException;
import com.cglee079.pododev.web.global.config.filter.CorsFilter;
import com.cglee079.pododev.web.global.config.security.oauth.GoogleUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.Filter;

public class SecurityUtil extends WebSecurityConfigurerAdapter {

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

        return ((GoogleUserDetails) principal).getGoogleIdentifier();
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


//        import lombok.RequiredArgsConstructor;
//        import org.springframework.context.ApplicationContext;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//        import org.springframework.security.config.annotation.web.builders.WebSecurity;
//        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//        import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//        import org.springframework.security.web.session.SessionManagementFilter;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final ApplicationContext context;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .cors().disable()
//
//                .authorizeRequests()
//                .antMatchers("/auth/login")
//                .permitAll()
//
//                .antMatchers("/auth/user")
//                .permitAll()
//
//                .antMatchers("/authentication")
//                .permitAll()
//
//                .addFilterBefore(new AuthFilter(securityStore()), BasicAuthenticationFilter.class)
//                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class);
//
//    }
//
//    @Bean
//    public SecurityStore securityStore() {
//        return new SecurityStore(1000L);
//    }
//}
//
