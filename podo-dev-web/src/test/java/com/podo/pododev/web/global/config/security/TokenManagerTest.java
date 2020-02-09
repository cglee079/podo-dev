package com.podo.pododev.web.global.config.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class TokenManagerTest {

    private static final String SECRET_KEY = "11111111111DFDFDFD1111111111AAAAAAAAAAAAAAABBBBBBBBBBCCCCCCCCCCC";

    @DisplayName("Authenticate, 성공")
    @Test
    void testAuthenticate01() {

        //given
        final TokenManager tokenManager = new TokenManager();

        ReflectionTestUtils.setField(tokenManager, "expireHour", 24);
        ReflectionTestUtils.setField(tokenManager, "secretKey", SECRET_KEY);

        final String token = tokenManager.createToken();

        //when
        final boolean result = tokenManager.authenticate(token);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("Authenticate, 시간 만료")
    @Test
    void testAuthenticate02() {

        //given
        final TokenManager tokenManager = new TokenManager();

        ReflectionTestUtils.setField(tokenManager, "expireHour", 0);
        ReflectionTestUtils.setField(tokenManager, "secretKey", SECRET_KEY);

        final String token = tokenManager.createToken();

        //when
        final boolean result = tokenManager.authenticate(token);

        //then
        assertThat(result).isFalse();
    }
}