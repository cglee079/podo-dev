package com.cglee079.pododev.web.domain.auth;

import com.cglee079.pododev.web.domain.auth.exception.NoAuthenticatedException;
import com.cglee079.pododev.web.global.config.security.SecurityStore;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import com.cglee079.pododev.web.global.config.security.oauth.GoogleUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log
@RequiredArgsConstructor
@Service
public class AuthService {

    private final SecurityStore securityStore;

    public UserDto.response getUser() {
        GoogleUserDetails userDetails = SecurityUtil.getUser();


        if (Objects.isNull(userDetails)) {
            throw new NoAuthenticatedException();
        }

        return new UserDto.response(userDetails);

    }


    /**
     * 사용자정보 삭제
     *
     * @param token
     */
    public void logout(String token) {
        securityStore.logout(token);
    }
}

