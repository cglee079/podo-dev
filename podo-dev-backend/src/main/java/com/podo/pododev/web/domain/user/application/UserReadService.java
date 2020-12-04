package com.podo.pododev.web.domain.user.application;

import com.podo.pododev.web.domain.user.exception.InvalidUserIdApiException;
import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.domain.user.repository.UserRepository;
import com.podo.pododev.web.domain.user.value.UserVo;
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

    public UserVo getUser(Long userId) {

        if(Objects.isNull(userId)){
            return null;
        }

        final Optional<User> userOptional = userRepository.findById(userId);
        final User user = userOptional.orElseThrow(() -> new InvalidUserIdApiException(userId));

        return UserVo.createByUser(user);
    }

    public Optional<UserRole> getRoleByKey(String userKey) {
        return  userRepository.findByUserKey(userKey).map(User::getRole);
    }
}

