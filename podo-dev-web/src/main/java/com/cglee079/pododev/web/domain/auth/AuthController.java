package com.cglee079.pododev.web.domain.auth;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.StatusResponse;
import com.cglee079.pododev.web.domain.user.UserDto;
import com.cglee079.pododev.web.domain.user.UserService;
import com.cglee079.pododev.web.global.config.security.SecurityStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final SecurityStore securityStore;

    @ResponseBody
    @GetMapping("/login/enabled")
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
                .data(result)
                .build();
    }

    @GetMapping("/login/google")
    public void loginGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth/google");
    }

    @ResponseBody
    @PostMapping("/logout")
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
