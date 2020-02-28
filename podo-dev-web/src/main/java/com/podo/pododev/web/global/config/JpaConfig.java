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
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            final Optional<OAuthUserDetails> userDetails = SecurityUtil.getUser();
            return userDetails.map(oAuthUserDetails -> Optional.of(oAuthUserDetails.getUserKey())).orElseGet(() -> Optional.of("Server"));
        }

    }
}
