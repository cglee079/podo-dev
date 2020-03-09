package com.podo.pododev.web.domain.user.application;

import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserDto;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.podo.pododev.web.global.config.security.role.UserRole.ADMIN;
import static com.podo.pododev.web.global.config.security.role.UserRole.USER;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("사용자 WRITE 통합 테스트")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserWriteServiceTest {

    private final UserWriteService userWriteService;
    private final UserRepository userRepository;

    @DisplayName("사용자 생성, 사용자 없을 때")
    @Test
    void testWriteUser01() {
        //given
        final String userKey = "123";
        final String username = "username";
        final String picture = "picture";
        final OAuthType oAuthType = OAuthType.GOOGLE;
        final UserRole role = USER;

        final UserDto.insert userInsert = UserDto.insert.builder()
                .role(role)
                .userKey(userKey)
                .name(username)
                .oAuthType(oAuthType)
                .picture(picture)
                .build();

        //when
        final Long userId = userWriteService.writeUser(userInsert);

        //then
        final Optional<User> optionalUser = userRepository.findById(userId);

        assertThat(optionalUser).isPresent();
        final User user = optionalUser.get();
        assertThat(user.getOAuthType()).isEqualTo(oAuthType);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getRole()).isEqualTo(role);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getUserKey()).isEqualTo(userKey);
    }

    @DisplayName("사용자 생성, USER 권한의 사용자가 있을 때")
    @Test
    void testWriteUser02() {
        //given
        final String userKey = "123";

        final Long existedUserId = saveExistedUser(userKey, USER);

        final String username = "username";
        final String picture = "picture";
        final OAuthType oAuthType = OAuthType.GOOGLE;

        final UserDto.insert userInsert = UserDto.insert.builder()
                .userKey(userKey)
                .name(username)
                .oAuthType(oAuthType)
                .picture(picture)
                .build();

        //when
        final Long userId = userWriteService.writeUser(userInsert);

        //then
        assertThat(userId).isEqualTo(existedUserId);
        final Optional<User> optionalUser = userRepository.findById(userId);

        assertThat(optionalUser).isPresent();
        final User user = optionalUser.get();
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getUserKey()).isEqualTo(userKey);
    }

    @DisplayName("사용자 생성, ADMIN 권한의 사용자가 있을 때")
    @Test
    void testWriteUser03() {
        //given
        final String userKey = "123";

        final Long existedUserId = saveExistedUser(userKey, ADMIN);

        final String username = "username";
        final String picture = "picture";
        final OAuthType oAuthType = OAuthType.GOOGLE;

        final UserDto.insert userInsert = UserDto.insert.builder()
                .userKey(userKey)
                .name(username)
                .oAuthType(oAuthType)
                .picture(picture)
                .build();

        //when
        final Long userId = userWriteService.writeUser(userInsert);

        //then
        assertThat(userId).isEqualTo(existedUserId);
        final Optional<User> optionalUser = userRepository.findById(userId);

        assertThat(optionalUser).isPresent();
        final User user = optionalUser.get();
        assertThat(user.getUserKey()).isEqualTo(userKey);
        assertThat(user.getUsername()).isNotEqualTo(username);
        assertThat(user.getPicture()).isNotEqualTo(picture);
    }

    private Long saveExistedUser(String userKey, UserRole role) {
        final User user = User.builder()
                .userKey(userKey)
                .role(role)
                .build();

        final User savedUser = userRepository.save(user);

        return savedUser.getId();
    }
}