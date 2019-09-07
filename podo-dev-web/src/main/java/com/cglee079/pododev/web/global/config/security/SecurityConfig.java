package com.cglee079.pododev.web.global.config.security;

import com.cglee079.pododev.core.global.config.filter.CorsFilter;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        web.ignoring().antMatchers("/login/google");
        //web.ignoring().antMatchers("**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();

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

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
