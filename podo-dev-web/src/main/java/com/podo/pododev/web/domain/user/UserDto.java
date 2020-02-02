package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.config.security.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.GoogleUserDetails;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class UserDto {

    @Getter
    public static class response {
        private String googleId;
        private String email;
        private String username;
        private String profileImage;
        private Boolean isAdmin;

        public static response createByGoogleUserDetails(GoogleUserDetails authUser) {
            final response response = new response();
            response.googleId = authUser.getGoogleId();
            response.username = authUser.getUsername();
            response.profileImage = authUser.getProfileImage();
            response.isAdmin = SecurityUtil.isAdmin();
            return response;
        }

    }

    @Getter
    public static class insert {
        private String userId;
        private String username;
        private String picture;

        @Builder
        public insert(@NotNull String userId, String name, String picture) {
            this.userId = userId;
            this.username = name;
            this.picture = picture;
        }

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .username(username)
                    .picture(picture)
                    .build();
        }
    }
}
