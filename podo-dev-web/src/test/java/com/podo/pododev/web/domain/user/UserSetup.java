package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.config.security.role.UserRole;
import com.podo.pododev.web.global.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserSetup {

    private final UserRepository userRepository;

    public User saveOne(){
        final User user = User.builder()
                .userKey(HashUtil.hash("google", LocalDateTime.now().toString()))
                .username("username")
                .picture("http://picure.com")
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }
}
