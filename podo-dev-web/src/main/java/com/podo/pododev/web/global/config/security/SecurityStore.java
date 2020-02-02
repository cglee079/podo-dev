package com.podo.pododev.web.global.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 인메모리 인증정보를 저장하는 정보입니다.
 */
@Slf4j
@Component
public class SecurityStore {

    private final Long expireHour;
    private final Map<String, Authentication> authentications;
    private final Map<String, LocalDateTime> lastAuthTimes;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    public SecurityStore(@Value("${security.expire.hour}") Long expireHour) {
        assert expireHour != null;

        this.expireHour = expireHour;
        this.authentications = new HashMap<>();
        this.lastAuthTimes = new HashMap<>();
    }

    public void loginByToken(String token, Authentication authentication, LocalDateTime dateTime) {
        authentications.put(token, authentication);
        lastAuthTimes.put(token, dateTime);

        scheduleRemoveToken(token);
    }

    private void scheduleRemoveToken(String token){
        scheduler.schedule(() -> {
            log.info("{} 인증시간이 만료되어 삭제합니다", token);
            this.authentications.remove(token);
            this.lastAuthTimes.remove(token);
        }, expireHour, TimeUnit.HOURS);
    }

    public Authentication isAuthUserByToken(String token, LocalDateTime dateTime) {
        // 인증되지 않은 사용자
        if(!authentications.containsKey(token)){
            return null;
        }

        // 마지막 접속 시간 갱신
        lastAuthTimes.put(token, dateTime);

        return authentications.get(token);
    }


    public void logoutByToken(String accessToken) {
        authentications.remove(accessToken);
        lastAuthTimes.remove(accessToken);
    }
}

