package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.config.security.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.GoogleUserDetails;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class response {
        private String googleIdentifier;
        private String email;
        private String name;
        private String picture;
        private Boolean isAdmin;

        public response(GoogleUserDetails authUser) {
            this.googleIdentifier = authUser.getGoogleId();
            this.email = authUser.getEmail();
            this.name = authUser.getUsername();
            this.picture = authUser.getPicture();
            this.isAdmin = SecurityUtil.isAdmin();
        }

    }

    @Getter
    public static class insert {
        private String userId;
        private String email;
        private String username;
        private String picture;

        @Builder
        public insert(String userId, String email, String name, String picture) {
            this.userId = userId;
            this.email = email;
            this.username = name;
            this.picture = picture;
        }

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .email(email)
                    .username(username)
                    .picture(picture)
                    .build();
        }
    }
}
