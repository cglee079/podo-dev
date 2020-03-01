package com.podo.pododev.web.domain.auth;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.core.rest.response.StatusResponse;
import com.podo.pododev.web.global.config.security.SecurityStore;
import com.podo.pododev.core.util.type.RequestHeader;
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
    public ApiResponse checkIsAllowedUserAgent(HttpServletRequest request) {
        final String userAgent = request.getHeader(RequestHeader.USER_AGENT.value());

        return DataResponse.success()
                .result(isAllowedUserAgent(userAgent))
                .build();
    }

    private boolean isAllowedUserAgent(String userAgent) {
        if(StringUtils.isEmpty(userAgent)){
            return true;
        }

        final Optional<String> first = Stream.of(NOT_ALLOWED_USER_AGENTS)
                .filter(s -> userAgent.toLowerCase().contains(s.toLowerCase()))
                .findFirst();

        return !first.isPresent();
    }

    @PostMapping("/api/logout")
    public ApiResponse logout(HttpServletRequest request) {

        final String authorization = request.getHeader(RequestHeader.AUTHORIZATION.value());

        if (Objects.nonNull(authorization)) {
            final String token = authorization.replace(AUTHORIZATION_HEADER_PREFIX, "").trim();
            securityStore.logout(token);
        }

        return StatusResponse.success();
    }

}
