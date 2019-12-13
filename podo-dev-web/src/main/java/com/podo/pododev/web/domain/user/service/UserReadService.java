package com.podo.pododev.web.domain.user.service;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedException;
import com.podo.pododev.web.global.config.security.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.GoogleUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class UserReadService {

    private final UserRepository userRepository;

    public UserDto.response getCurrentUser() {
        final GoogleUserDetails googleUserDetails = SecurityUtil.getUser();

        if (Objects.isNull(googleUserDetails)) {
            throw new NoAuthenticatedException();
        }

        return UserDto.response.createByGoogleUserDetails(googleUserDetails);
    }

}

