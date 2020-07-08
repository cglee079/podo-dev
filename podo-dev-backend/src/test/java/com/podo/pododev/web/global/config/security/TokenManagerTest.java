package com.podo.pododev.web.global.config.security;

import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.domain.user.value.UserVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("토큰 관리자")
class TokenManagerTest {

    private static final String SECRET_KEY = "11111111111DFDFDFD1111111111AAAAAAAAAAAAAAABBBBBBBBBBCCCCCCCCCCC";

    @DisplayName("Authenticate, 성공")
    @Test
    void testAuthenticate01() {
        //given
        final TokenManager tokenManager = new TokenManager(24, SECRET_KEY);

        final UserVo userVo = UserVo.createByUser(User.builder().build());
        final String token = tokenManager.createToken(userVo);

        //when
        final boolean result = tokenManager.authenticate(token);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("Authenticate, 시간 만료")
    @Test
    void testAuthenticate02() {
        //given
        final TokenManager tokenManager = new TokenManager(0, SECRET_KEY);

        final UserVo userVo = UserVo.createByUser(User.builder().build());
        final String token = tokenManager.createToken(userVo);

        //when
        final boolean result = tokenManager.authenticate(token);

        //then
        assertThat(result).isFalse();
    }
}
