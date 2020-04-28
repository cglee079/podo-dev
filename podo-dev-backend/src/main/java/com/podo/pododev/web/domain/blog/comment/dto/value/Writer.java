package com.podo.pododev.web.domain.blog.comment.dto.value;

import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.role.UserRole;
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
