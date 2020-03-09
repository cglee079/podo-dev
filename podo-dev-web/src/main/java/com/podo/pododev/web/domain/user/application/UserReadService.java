package com.podo.pododev.web.domain.user.application;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.InvalidUserIdApiException;
import com.podo.pododev.web.global.config.security.role.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserReadService {

    private final UserRepository userRepository;

    public UserDto.response getUser(Long userId) {

        if(Objects.isNull(userId)){
            return null;
        }

        final Optional<User> userOptional = userRepository.findById(userId);
        final User user = userOptional.orElseThrow(() -> new InvalidUserIdApiException(userId));

        return UserDto.response.createByUser(user);
    }

    public Optional<UserRole> getRoleByKey(String userKey) {
        return  userRepository.findByUserKey(userKey).map(User::getRole);
    }
}

