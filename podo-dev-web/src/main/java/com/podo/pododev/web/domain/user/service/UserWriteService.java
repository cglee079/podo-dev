package com.podo.pododev.web.domain.user.service;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.global.util.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Transactional
@Service
public class UserWriteService {

    private final UserRepository userRepository;

    public Long writeUser(UserDto.insert insert) {

        final Optional<User> userOptional = userRepository.findByUserKey(insert.getUserKey());

        if (!userOptional.isPresent()) {
            final User newUser = insert.toEntity();
            return userRepository.save(newUser).getId();
        }

        final User existedUser = userOptional.get();

        final String newPicture = insert.getPicture();
        final String newUsername = insert.getUsername();

        existedUser.updateUserInfo(newUsername, newPicture);

        return existedUser.getId();
    }
}

