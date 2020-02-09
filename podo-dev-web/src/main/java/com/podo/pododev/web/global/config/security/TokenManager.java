package com.podo.pododev.web.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class TokenManager {

    private static final JacksonSerializer<Map<String, ?>> SERIALIZER = new JacksonSerializer<>(new ObjectMapper());

    @Value("${security.expire.hour}")
    private long expireHour;

    @Value("${security.secret-key}")
    private String secretKey;

    public String createToken() {
        return Jwts.builder()
                .serializeToJsonWith(SERIALIZER)
                .setExpiration(new Date(System.currentTimeMillis() + (expireHour * 60 * 60 * 1000)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean authenticate(String token) {
        try {

            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
