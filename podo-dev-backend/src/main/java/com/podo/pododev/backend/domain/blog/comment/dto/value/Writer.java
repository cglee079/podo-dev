package com.podo.pododev.backend.domain.blog.comment.dto.value;

import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.role.UserRole;
import lombok.Getter;

@Getter
public class Writer {

    private OAuthType oAuthType;
    private String username;
    private Boolean isAdmin;

    public Writer(User user) {
        this.oAuthType = user.getOAuthType();
        this.username = user.getUsername();
        this.isAdmin = user.getRole().equals(UserRole.ADMIN);
    }
}
