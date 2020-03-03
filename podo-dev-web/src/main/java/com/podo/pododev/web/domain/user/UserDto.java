package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.role.UserRole;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import lombok.Builder;
import lombok.Getter;

import static com.podo.pododev.web.global.config.security.role.UserRole.ADMIN;

public class UserDto {

    @Getter
    public static class summary {
        private OAuthType oAuthType;
        private String username;
        private String picture;
        private UserRole role;

        public summary(User user) {
            this.oAuthType = user.getOAuthType();
            this.picture = user.getPicture();
            this.role = user.getRole();
            this.username = user.getUsername();
        }

    }

    @Getter
    public static class response {
        private OAuthType oAuthType;
        private String username;
        private String picture;
        private Boolean isAdmin;

        public static response createByUser(User user) {
            final response response = new response();
            response.oAuthType = user.getOAuthType();
            response.username = user.getUsername();
            response.picture = user.getPicture();
            response.isAdmin = user.getRole().equals(ADMIN);
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
