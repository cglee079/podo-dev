package com.podo.pododev.web.global.config;

import com.podo.pododev.web.global.util.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
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
