package com.podo.pododev.web.domain.auth;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.core.rest.response.StatusResponse;
import com.podo.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class AuthApi {

    private static final String[] NOT_ALLOWED_USER_AGENTS = {"KAKAOTALK"};

    private final SecurityStore securityStore;

    @GetMapping("/api/login/success")
    public ApiResponse checkIsAllowdedUserAgent(HttpServletRequest request, Authentication authentication) {
        final SecurityContext context = SecurityContextHolder.getContext();
        final String userAgent = request.getHeader("User-Agent");

        final boolean result = isAllowedUserAgent(userAgent);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(result)
                .build();
    }

    @GetMapping("/api/login/enabled")
    public ApiResponse checkIsAllowedUserAgent(HttpServletRequest request) {
        final String userAgent = request.getHeader("User-Agent");

        final boolean result = isAllowedUserAgent(userAgent);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(result)
                .build();
    }

    private boolean isAllowedUserAgent(String userAgent) {
        for (String notAllowedUserAgent : NOT_ALLOWED_USER_AGENTS) {
            if (userAgent.contains(notAllowedUserAgent)) {
                return false;
            }
        }

        return true;
    }

    @PostMapping("/api/logout")
    public ApiResponse logout(HttpServletRequest request) {

        final String authorization = request.getHeader("Authorization");

        if (Objects.nonNull(authorization)) {
            final String token = authorization.replace("bearer", "").trim();
            securityStore.logout(token);
        }

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
