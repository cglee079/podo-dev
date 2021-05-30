package com.podo.pododev.backend.domain.user.application;

import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.domain.user.repository.UserRepository;
import com.podo.pododev.backend.domain.user.value.UserVo;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
@DisplayName("사용자 조회 통합 테스트")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserReadServiceTest {

    private final UserReadService userReadService;
    private final UserRepository userRepository;

    @DisplayName("사용자 정보 조회")
    @Test
    void testGetUserInfo(){
        //given
        final String username = "username";
        final String picture = "picture";
        final String userKey = "userKey";
        final UserRole userRole = UserRole.USER;
        final OAuthType oAuthType = OAuthType.GOOGLE;
        final User user = User.builder()
                .username(username)
                .picture(picture)
                .userKey(userKey)
                .role(userRole)
                .oAuthType(oAuthType)
                .build();

        final User savedUser = userRepository.save(user);

        //when
        final UserVo userVo = userReadService.getUser(savedUser.getId());

        //then
        assertThat(userVo.getUsername()).isEqualTo(username);
        assertThat(userVo.getOAuthType()).isEqualTo(oAuthType);
        assertThat(userVo.getPicture()).isEqualTo(picture);
        assertThat(userVo.getIsAdmin()).isFalse();
    }

    @DisplayName("사용자 Role 조회, 사용자가 없을 때")
    @Test
    void testGetUserRole01(){
        //when
        final Optional<UserRole> role = userReadService.getRoleByKey("123");

        //then
        assertThat(role).isEmpty();
    }

    @DisplayName("사용자 Role 조회, 사용자가 있을 때")
    @Test
    void testGetUserRole02(){
        //given
        final String userKey = "123";
        final UserRole userRole = UserRole.ADMIN;
        final User user = User.builder()
                .userKey(userKey)
                .role(userRole)
                .build();

        userRepository.save(user);

        //when
        final Optional<UserRole> role = userReadService.getRoleByKey(userKey);

        //then
        assertThat(role).hasValue(UserRole.ADMIN);
    }
}
