package com.podo.pododev.web.domain.auth;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.web.global.security.SecurityStore;
import com.podo.pododev.web.global.security.oauth.OAuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
public class AuthApi {

    private static final String[] NOT_ALLOWED_USER_AGENTS = {"KAKAOTALK"};
    private static final String AUTHORIZATION_HEADER_PREFIX = "bearer";

    private final SecurityStore securityStore;

    @GetMapping("/api/login/enabled")
    public boolean checkIsAllowedUserAgent(HttpServletRequest request, String oAuthType) {
        final String userAgent = request.getHeader(RequestHeader.USER_AGENT.value());
        return isAllowedUserAgent(userAgent, oAuthType);
    }

    private boolean isAllowedUserAgent(String userAgent, String oAuthType) {
        if (StringUtils.isEmpty(userAgent)) {
            return true;
        }

        final Optional<String> first = Stream.of(NOT_ALLOWED_USER_AGENTS)
                .filter(s -> userAgent.toLowerCase().contains(s.toLowerCase()) && oAuthType.equalsIgnoreCase(OAuthType.GOOGLE.name()))
                .findFirst();

        return !first.isPresent();
    }

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request) {
        final String authorization = request.getHeader(RequestHeader.AUTHORIZATION.value());

        if (Objects.nonNull(authorization)) {
            final String token = authorization.replace(AUTHORIZATION_HEADER_PREFIX, "").trim();
            securityStore.logout(token);
        }
    }

}
