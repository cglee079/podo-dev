package com.podo.pododev.backend.global.web;

import com.podo.pododev.backend.global.security.oauth.OAuthUserDetails;
import com.podo.pododev.backend.global.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<Long> {

        @Override
        public Optional<Long> getCurrentAuditor() {
            final Optional<OAuthUserDetails> userDetails = SecurityUtil.getUser();
            return userDetails.map(OAuthUserDetails::getUserId);
        }

    }
}
