package com.podo.pododev.web.domain.user.application.helper;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.InvalidUserIdApiException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class UserServiceHelper {

    public static User getCurrentUser(Long userId, UserRepository userRepository) {
        final Optional<User> currentUserOptional = userRepository.findById(userId);
        return currentUserOptional.orElseThrow(() -> new InvalidUserIdApiException(userId));
    }

}
