package com.cglee079.pododev.web.domain.auth;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 후 프론트엔드 사용자 정보 갱신
     *
     * @return 사용자 정보
     */
    @GetMapping("/auth/user")
    public ApiResponse getUserInfo() {
        UserDto.response user = authService.getUser();
        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(user)
                .build();
    }

    @PostMapping("/auth/logout")
    public ApiResponse logout(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (null != token) {
            token = token.replace("bearer", "");
            token = token.trim();

            authService.logout(token);
        }

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();


    }

}
