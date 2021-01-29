package com.podo.pododev.web.global.security;

import com.podo.pododev.web.domain.user.value.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityStore {

    private final TokenManager tokenManager;
    private final Map<String, Authentication> authentications = new HashMap<>();

    public String login(Authentication authentication, UserVo userVo) {
        final String token = tokenManager.createToken(userVo);
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

