package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.role.UserRole;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class response {
        private OAuthType oAuthType;
        private String username;
        private String picture;
        private Boolean isAdmin;

        public static response createByUserDetails(OAuthUserDetails authUser, Boolean admin) {
            final response response = new response();
            response.oAuthType = authUser.getOAuthType();
            response.username = authUser.getUsername();
            response.picture = authUser.getPicture();
            response.isAdmin = admin;
            return response;
        }

    }

    @Getter
    public static class insert {
        private OAuthType oAuthType;
        private String userKey;
        private String username;
        private String picture;
        private UserRole role;

        @Builder
        public insert(OAuthType oAuthType, String userKey, String name, String picture, UserRole role) {
            this.oAuthType = oAuthType;
            this.userKey = userKey;
            this.username = name;
            this.picture = picture;
            this.role = role;
        }

        public User toEntity() {
            return User.builder()
                    .oAuthType(oAuthType)
                    .userKey(userKey)
                    .username(username)
                    .picture(picture)
                    .role(role)
                    .build();
        }
    }
}
