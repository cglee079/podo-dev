package com.podo.pododev.web.domain.auth;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.core.rest.response.StatusResponse;
import com.podo.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AuthApiController {

    private final SecurityStore securityStore;

    @GetMapping("/api/login/enabled")
    public ApiResponse loginGoogle(HttpServletRequest request) {
        final String[] notAllowedUserAgent = {"KAKAOTALK"};
        final String userAgent = request.getHeader("User-Agent");

        boolean result = true;

        for (String notAllowed : notAllowedUserAgent) {
            if (userAgent.contains(notAllowed)) {
                result = false;
                break;
            }
        }

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(result)
                .build();
    }

    @ResponseBody
    @PostMapping("/api/logout")
    public ApiResponse logout(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (null != token) {
            token = token.replace("bearer", "");
            token = token.trim();

            securityStore
                    .logout(token);
        }

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
