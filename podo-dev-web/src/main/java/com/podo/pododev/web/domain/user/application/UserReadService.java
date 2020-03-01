package com.podo.pododev.web.domain.user.application;

import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedApiException;
import com.podo.pododev.web.global.util.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class UserReadService {


    public UserDto.response getCurrentUser() {
        final Optional<OAuthUserDetails> oAuthUserDetails = SecurityUtil.getUser();
        return UserDto.response.createByUserDetails(oAuthUserDetails.orElseThrow(NoAuthenticatedApiException::new));
    }

}

