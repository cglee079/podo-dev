package com.cglee079.pododev.web.domain.user;

import com.cglee079.pododev.web.domain.user.exception.NoAuthenticatedException;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import com.cglee079.pododev.web.global.config.security.oauth.GoogleUserDetails;
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
        GoogleUserDetails userDetails = SecurityUtil.getUser();

        if (Objects.isNull(userDetails)) {
            throw new NoAuthenticatedException();
        }

        return new UserDto.response(userDetails);
    }

    public void save(UserDto.insert insert) {

        final Optional<User> userOptional = userRepository.findByUserId(insert.getUserId());

        if (!userOptional.isPresent()) {
            userRepository.save(insert.toEntity());
            return;
        }

        final User user = userOptional.get();
        final String newEmail = insert.getEmail();
        final String newPicture = insert.getPicture();
        final String newUsername = insert.getUsername();

        if (user.isChanged(newUsername, newEmail, newPicture)) {
            user.updateInfo(newUsername, newEmail, newPicture);
        }

    }
}

