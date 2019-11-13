package com.cglee079.pododev.web.domain.user;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    /**
     * 로그인 후 프론트엔드 사용자 정보 갱신
     *
     * @return 사용자 정보
     */
    @GetMapping("/api/user")
    public ApiResponse getUserInfo() {
        UserDto.response user = userService.getUser();
        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(user)
                .build();
    }


}
