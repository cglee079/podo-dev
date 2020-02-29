package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.global.config.security.role.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String userKey;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private List<SimpleGrantedAuthority> authorities;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String userKey, String nameAttributeKey, String name, String email, String picture, List<SimpleGrantedAuthority> authorities) {
        this.attributes = attributes;
        this.userKey = userKey;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authorities = authorities;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes, List<String> adminKeys){
        return ofGoogle(usernameAttributeName, attributes, adminKeys);
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes, List<String> adminKeys) {
        final String userKey = (String) attributes.get(usernameAttributeName);

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));

        if (adminKeys.contains(userKey)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN));
        }

        return OAuthAttributes.builder()
                .userKey(userKey)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .nameAttributeKey(usernameAttributeName)
                .attributes(attributes)
                .authorities(authorities)
                .build();
    }

}
