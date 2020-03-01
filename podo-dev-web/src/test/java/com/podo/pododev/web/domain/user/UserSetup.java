package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSetup {

    private final UserRepository userRepository;

    public User saveOne(){
        final User user = User.builder()
                .userKey(HashUtil.hash("google", "123123"))
                .username("username")
                .picture("http://picure.com")
                .build();

        return userRepository.save(user);
    }
}
