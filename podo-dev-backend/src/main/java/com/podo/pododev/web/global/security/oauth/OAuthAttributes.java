package com.podo.pododev.web.global.security.oauth;

import com.podo.pododev.web.global.security.role.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@Getter
public class OAuthAttributes {

    private OAuthType oAuthType;
    private String email;
    private String userKey;
    private String username;
    private String picture;
    private UserRole role;

    @Builder
    public OAuthAttributes(OAuthType oAuthType, String email, String userKey, String username, String picture) {
        this.email = email;
        this.oAuthType = oAuthType;
        this.userKey = userKey;
        this.username = username;
        this.picture = picture;
    }

    public void setRole(UserRole role){
        this.role = role;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
