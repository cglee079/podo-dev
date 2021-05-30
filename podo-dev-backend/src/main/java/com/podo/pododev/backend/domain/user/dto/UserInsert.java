package com.podo.pododev.backend.domain.user.dto;

import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.role.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInsert {

    private OAuthType oAuthType;
    private String userKey;
    private String email;
    private String username;
    private String picture;
    private UserRole role;

    @Builder
    public UserInsert(OAuthType oAuthType, String email, String userKey, String name, String picture, UserRole role) {
        this.oAuthType = oAuthType;
        this.email = email;
        this.userKey = userKey;
        this.username = name;
        this.picture = picture;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .oAuthType(oAuthType)
                .email(email)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .role(role)
                .build();
    }
}
