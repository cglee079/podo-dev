package com.cglee079.pododev.web.global.config;

import com.cglee079.pododev.web.global.config.security.oauth.GoogleAuthentication;
import com.cglee079.pododev.web.global.config.security.oauth.GoogleUserDetails;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.ws.rs.core.SecurityContext;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!Objects.isNull(authentication)) {

                final Object principal = authentication.getPrincipal();

                if (principal instanceof GoogleUserDetails) {
                    final GoogleUserDetails userDetails = (GoogleUserDetails) principal;
                    return Optional.of(userDetails.getGoogleIdentifier());
                }
            }

            return Optional.of("");
        }
    }
}
