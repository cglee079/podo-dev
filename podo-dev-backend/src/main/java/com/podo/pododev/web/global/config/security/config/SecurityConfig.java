package com.podo.pododev.web.global.config.security.config;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.web.global.config.security.SecurityStore;
import com.podo.pododev.web.global.config.security.filter.CorsFilter;
import com.podo.pododev.web.global.config.security.filter.TokenAuthFilter;
import com.podo.pododev.web.global.config.security.oauth.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.podo.pododev.web.global.config.security.role.UserRole.ADMIN;
import static com.podo.pododev.web.global.config.security.role.UserRole.USER;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.login.success.url}")
    private String longSuccessUrl;

    private final OAuth2Service oauth2Service;
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

        http.authorizeRequests().expressionHandler(expressionHandler());

        //ADMIN 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs").hasRole(ADMIN.name());
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*").hasRole(ADMIN.name());
        http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/blogs/*").hasRole(ADMIN.name());

        //USER 권한
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/blogs/*/comments").hasRole(USER.name());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/blogs/*/comments").hasRole(USER.name());

        http.addFilterBefore(new CorsFilter(), SessionManagementFilter.class);
        http.addFilterBefore(new TokenAuthFilter(securityStore), BasicAuthenticationFilter.class);

        http.oauth2Login()
                .successHandler((req, res, auth) -> res.sendRedirect(longSuccessUrl +"?accessToken=" + res.getHeader(RequestHeader.ACCESS_TOKEN.value())))
                .userInfoEndpoint().userService(oauth2Service);
    }

    public SecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(String.format("ROLE_%s > ROLE_%s", ADMIN.name(), USER.name()));

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();

        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

}

