package com.podo.pododev.web.domain.user;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        UserDto.response user = userService.getCurrentUser();
        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(user)
                .build();
    }


}
