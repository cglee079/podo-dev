package com.podo.pododev.web.global.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 인메모리 인증정보를 저장하는 정보입니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityStore {

    private final TokenManager tokenManager;
    private final Map<String, Authentication> authentications = new HashMap<>();

    public String login(Authentication authentication) {
        final String token = tokenManager.createToken();
        authentications.put(token, authentication);
        return token;
    }

    public Authentication getAuthentication(String token){
        if(tokenManager.authenticate(token)){
            return authentications.get(token);
        }
        return null;
    }

    public void logout(String accessToken) {
        authentications.remove(accessToken);
    }
}

