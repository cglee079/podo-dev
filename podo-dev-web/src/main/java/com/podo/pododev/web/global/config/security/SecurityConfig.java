package com.podo.pododev.web.global.config.security;

import com.podo.pododev.web.global.config.security.oauth.CustomOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.login.success.url}")
    private String longSuccessUrl;

    private final CustomOAuth2Service customOauth2Service;
    private final SecurityStore securityStore;

    @Override
    public void configure(WebSecurity web) {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.logout().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //ADMIN 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs").hasRole(UserRole.ADMIN.name());
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*").hasRole(UserRole.ADMIN.name());
        http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/blogs/*").hasRole(UserRole.ADMIN.name());

        //USER 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*/comment").hasRole(UserRole.USER.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/blogs/*/comment").hasRole(UserRole.USER.name());

        http.addFilterBefore(new CorsFilter(), SessionManagementFilter.class);
        http.addFilterBefore(new AuthFilter(securityStore), BasicAuthenticationFilter.class);

        http.oauth2Login()
                .successHandler((request, response, authentication) -> response.sendRedirect(longSuccessUrl +"?accessToken=" + response.getHeader("Access-Token")))
                .userInfoEndpoint().userService(customOauth2Service);
    }


}

