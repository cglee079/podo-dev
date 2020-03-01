package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.global.config.security.role.UserRole;
import com.podo.pododev.web.global.util.HashUtil;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String registrationId;
    private String userKey;
    private String userKeyAttributeName;
    private String name;
    private String email;
    private String picture;
    private List<SimpleGrantedAuthority> authorities;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String registrationId, String userKey, String userKeyAttributeName, String name, String email, String picture, List<SimpleGrantedAuthority> authorities) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.userKey = userKey;
        this.userKeyAttributeName = userKeyAttributeName;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authorities = authorities;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes, List<String> adminKeys){
        return ofGoogle(registrationId, usernameAttributeName, attributes, adminKeys);
    }

    private static OAuthAttributes ofGoogle(String registrationId, String userKeyAttributeName, Map<String, Object> attributes, List<String> adminKeys) {
        final String userKey = HashUtil.hash(registrationId, (String) attributes.get(userKeyAttributeName));

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));

        if (adminKeys.contains(userKey)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN));
        }

        return OAuthAttributes.builder()
                .userKey(userKey)
                .userKeyAttributeName(userKeyAttributeName)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .authorities(authorities)
                .build();
    }

}
