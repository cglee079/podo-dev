package com.podo.pododev.web.global.config.security;

import com.podo.pododev.web.global.config.filter.CorsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.Filter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("socialOauthFilter")
    private final Filter socialOauthFilter;
    private final SecurityStore securityStore;

    @Override
    public void configure(WebSecurity web) {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();

        http.logout().disable();

        //ADMIN 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs").hasRole(UserRole.ADMIN.name());
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*").hasRole(UserRole.ADMIN.name());

        //USER 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*/comment").hasRole(UserRole.USER.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/blogs/*/comment").hasRole(UserRole.USER.name());

        http.addFilterBefore(new CorsFilter(), SessionManagementFilter.class);
        http.addFilterBefore(socialOauthFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(new AuthFilter(securityStore), BasicAuthenticationFilter.class);
    }

}
