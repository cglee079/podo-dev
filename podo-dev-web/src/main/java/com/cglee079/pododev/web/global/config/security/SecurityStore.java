package com.cglee079.pododev.web.global.config.security;

import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 인메모리 인증정보를 저장하는 정보입니다.
 */
public class SecurityStore {

    private final Long expireTime;

    private final Map<String, Authentication> authentications;
    private final Map<String, LocalDateTime> lastConnectTime; //마지막 접속 시간

    public SecurityStore(Long expireTime) {
        this.expireTime = expireTime;
        this.authentications = new HashMap<>();
        this.lastConnectTime = new HashMap<>();
    }

    public void login(String token, Authentication authentication) {
        authentications.put(token, authentication);
        lastConnectTime.put(token, LocalDateTime.now());
    }

    public Authentication isAuth(String token) {
        // 인증되지 않은 사용자
        if(!authentications.containsKey(token)){
            return null;
        }

        // 계정 시간 만료
        if(LocalDateTime.now().minusMinutes(expireTime).isAfter(lastConnectTime.get(token))){
            authentications.remove(token);
            lastConnectTime.remove(token);
            return null;
        }


        // 마지막 접속 시간 갱신
        lastConnectTime.put(token, LocalDateTime.now());

        return authentications.get(token);
    }


    public void logout(String accessToken) {
        authentications.remove(accessToken);
        lastConnectTime.remove(accessToken);
    }
}

