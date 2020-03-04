package com.podo.pododev.web.domain.user.api;

import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.application.UserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserReadApi {

    private final UserReadService userReadService;

    @GetMapping("/api/user")
    public UserDto.response getUserInfo() {
        return userReadService.getCurrentUser();
    }

}
