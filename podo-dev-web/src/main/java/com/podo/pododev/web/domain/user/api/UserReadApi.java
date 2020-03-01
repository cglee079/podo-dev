package com.podo.pododev.web.domain.user.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.application.UserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserReadApi {

    private final UserReadService userReadService;

    @GetMapping("/api/user")
    public ApiResponse getUserInfo() {
        UserDto.response user = userReadService.getCurrentUser();
        return DataResponse.success()
                .result(user)
                .build();
    }


}
