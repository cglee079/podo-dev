package com.podo.pododev.backend.global.security;

import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.domain.user.value.UserVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@DisplayName("토큰 저장소")
class SecurityStoreTest {

    @DisplayName("New Instance")
    @Test
    void testNewInstance01() {
        assertDoesNotThrow(() -> new SecurityStore(Mockito.mock(TokenManager.class)));
    }

    @DisplayName("Auth, 성공")
    @Test
    void testAuthSuccess(){

        //given
        final TokenManager tokenManager = Mockito.mock(TokenManager.class);
        final SecurityStore securityStore = new SecurityStore(tokenManager);
        final Authentication authentication = Mockito.mock(Authentication.class);
        final UserVo userVo = UserVo.createByUser(User.builder().build());
        final String token = securityStore.login(authentication, userVo);

        given(tokenManager.authenticate(token)).willReturn(true);

        //when
        final Authentication resultAuthentication = securityStore.getAuthentication(token);

        //then
        assertThat(resultAuthentication).isEqualTo(authentication);
    }

    @DisplayName("Auth, 인증되지 않은 Token")
    @Test
    void testNoAuth(){
        //given
        final TokenManager tokenManager = Mockito.mock(TokenManager.class);
        final SecurityStore securityStore = new SecurityStore(tokenManager);
        final Authentication authentication = Mockito.mock(Authentication.class);
        final UserVo userVo = UserVo.createByUser(User.builder().build());
        final String token = securityStore.login(authentication, userVo);

        given(tokenManager.authenticate(token)).willReturn(false);

        //when
        final Authentication resultAuthentication = securityStore.getAuthentication(token);

        //then
        assertThat(resultAuthentication).isNull();
    }

    @DisplayName("Logout")
    @Test
    void testLogout(){
        //given
        final TokenManager tokenManager = Mockito.mock(TokenManager.class);
        final SecurityStore securityStore = new SecurityStore(tokenManager);
        final Authentication authentication = Mockito.mock(Authentication.class);
        final UserVo userVo = UserVo.createByUser(User.builder().build());
        final String token = securityStore.login(authentication, userVo);

        given(tokenManager.authenticate(token)).willReturn(true);

        //when
        securityStore.logout(token);

        //then
        assertThat(securityStore.getAuthentication(token)).isNull();
    }
}
