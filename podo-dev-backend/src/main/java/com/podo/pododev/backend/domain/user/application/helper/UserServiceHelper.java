package com.podo.pododev.backend.domain.user.application.helper;

import com.podo.pododev.backend.domain.user.exception.InvalidUserIdApiException;
import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.domain.user.repository.UserRepository;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class UserServiceHelper {

    public static User getCurrentUser(Long userId, UserRepository userRepository) {
        final Optional<User> currentUserOptional = userRepository.findById(userId);
        return currentUserOptional.orElseThrow(() -> new InvalidUserIdApiException(userId));
    }

}
