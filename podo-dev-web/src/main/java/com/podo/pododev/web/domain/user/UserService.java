package com.podo.pododev.web.domain.user;

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
public class UserService {

    private final UserRepository userRepository;

    public UserDto.response getUser() {
        final GoogleUserDetails userDetails = SecurityUtil.getUser();

        if (Objects.isNull(userDetails)) {
            throw new NoAuthenticatedException();
        }

        return new UserDto.response(userDetails);
    }

    public void merge(UserDto.insert insert) {

        final Optional<User> userOptional = userRepository.findByUserId(insert.getUserId());

        if (!userOptional.isPresent()) {
            final User newUser = insert.toEntity();
            userRepository.save(newUser);
            return;
        }

        final User existedUser = userOptional.get();

        final String newEmail = insert.getEmail();
        final String newPicture = insert.getPicture();
        final String newUsername = insert.getUsername();

        existedUser.updateUserInfo(newUsername, newEmail, newPicture);
    }
}

