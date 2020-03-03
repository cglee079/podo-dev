package com.podo.pododev.web.domain.user.application;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedApiException;
import com.podo.pododev.web.global.config.security.role.UserRole;
import com.podo.pododev.web.global.util.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserReadService {

    private final UserRepository userRepository;

    public UserDto.response getCurrentUser() {
        final Optional<OAuthUserDetails> oAuthUserDetails = SecurityUtil.getUser();
        return UserDto.response.createByUserDetails(oAuthUserDetails.orElseThrow(NoAuthenticatedApiException::new), SecurityUtil.isAdmin());
    }

    public UserRole getRoleByKey(String userKey) {
        final Optional<User> user = userRepository.findByUserKey(userKey);
        if (user.isPresent()) {
            return user.get().getRole();
        }

        return UserRole.USER;
    }
}

