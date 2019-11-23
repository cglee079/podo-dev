package com.podo.pododev.web.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 인메모리 인증정보를 저장하는 정보입니다.
 */
@Component
public class SecurityStore {


    @Value("${security.expire.time}")
    private Long expireTime;

    private final Map<String, Authentication> authentications;
    private final Map<String, LocalDateTime> lastConnectTime; //마지막 접속 시간

    public SecurityStore() {
        this.authentications = new HashMap<>();
        this.lastConnectTime = new HashMap<>();
    }

    public void loginByToken(String token, Authentication authentication) {
        authentications.put(token, authentication);
        lastConnectTime.put(token, LocalDateTime.now());
    }

    public Authentication isAuthUserByToken(String token) {
        // 인증되지 않은 사용자
        if(!authentications.containsKey(token)){
            return null;
        }

        // 계정 시간 만료
        if(LocalDateTime.now().minusHours(expireTime).isAfter(lastConnectTime.get(token))){
            authentications.remove(token);
            lastConnectTime.remove(token);
            return null;
        }


        // 마지막 접속 시간 갱신
        lastConnectTime.put(token, LocalDateTime.now());

        return authentications.get(token);
    }


    public void logoutByToken(String accessToken) {
        authentications.remove(accessToken);
        lastConnectTime.remove(accessToken);
    }
}

