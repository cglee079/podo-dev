package com.cglee079.pododev.web.global.config.security;

import com.cglee079.pododev.web.global.config.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

//TODO Security Setting
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web){

        //web.ignoring().antMatchers("**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilterBefore(new CorsFilter(), SessionManagementFilter.class);
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
