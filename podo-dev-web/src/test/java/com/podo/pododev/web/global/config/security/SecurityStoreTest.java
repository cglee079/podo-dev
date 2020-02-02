package com.podo.pododev.web.global.config.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityStoreTest {

    @DisplayName("New Instance")
    @Test
    void testNewInstance01() {
        assertDoesNotThrow(() -> new SecurityStore(1L));
    }

    @DisplayName("New Instance, Assertion Error")
    @ParameterizedTest(name = "{displayName} - {0}")
    @NullSource
    void testNewInstance02(Long expireHour) {
        assertThrows(AssertionError.class, () -> new SecurityStore(expireHour));
    }

    @DisplayName("Auth, 성공")
    @Test
    void testAuthSuccess(){

        //given
        final SecurityStore securityStore = new SecurityStore(1L);

        final String token = "token";
        final Authentication authentication = Mockito.mock(Authentication.class);

        final LocalDateTime dateTime = LocalDateTime.now();

        securityStore.loginByToken(token, authentication, dateTime);

        //when
        final Authentication resultAuthentication = securityStore.isAuthUserByToken(token, dateTime);

        //then
        assertThat(resultAuthentication).isEqualTo(authentication);
    }

    @DisplayName("Auth, 인증되지 않은 Token")
    @Test
    void testNoAuth(){

        //given
        final SecurityStore securityStore = new SecurityStore(1L);

        final String token = "token";
        final Authentication authentication = Mockito.mock(Authentication.class);
        final LocalDateTime dateTime = LocalDateTime.now();

        securityStore.loginByToken(token, authentication, dateTime);

        //when
        final Authentication resultAuthentication = securityStore.isAuthUserByToken("invalidToken", LocalDateTime.now());

        //then
        assertThat(resultAuthentication).isNull();
    }

    @DisplayName("Auth, 시간 만료")
    @Test
    void testExpireAuth() throws InterruptedException {

        //given
        final SecurityStore securityStore = new SecurityStore(0L);

        final String token = "token";
        final Authentication authentication = Mockito.mock(Authentication.class);
        final LocalDateTime dateTime = LocalDateTime.now();

        securityStore.loginByToken(token, authentication, dateTime);

        //when
        Thread.sleep(100);
        final Authentication resultAuthentication = securityStore.isAuthUserByToken(token, LocalDateTime.now());

        //then
        assertThat(resultAuthentication).isNull();
    }

    @DisplayName("Logout")
    @Test
    void testLogout(){

        //given
        final SecurityStore securityStore = new SecurityStore(1L);

        final String token = "token";
        final Authentication authentication = Mockito.mock(Authentication.class);
        final LocalDateTime dateTime = LocalDateTime.now();

        securityStore.loginByToken(token, authentication, dateTime);

        //when
        securityStore.logoutByToken(token);

        //then
        assertThat(securityStore.isAuthUserByToken(token, LocalDateTime.now())).isNull();
    }
}