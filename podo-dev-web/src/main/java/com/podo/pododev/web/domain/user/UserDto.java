package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.global.util.SecurityUtil;
import com.podo.pododev.web.global.config.security.oauth.OAuthUserDetails;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class UserDto {

    @Getter
    public static class response {
        private String googleId;
        private String username;
        private String profileImage;
        private Boolean isAdmin;

        public static response createByUserDetails(OAuthUserDetails authUser) {
            final response response = new response();
            response.googleId = authUser.getUserKey();
            response.username = authUser.getUsername();
            response.profileImage = authUser.getProfileImage();
            response.isAdmin = SecurityUtil.isAdmin();
            return response;
        }

    }

    @Getter
    public static class insert {
        private String userKey;
        private String username;
        private String picture;

        @Builder
        public insert(@NotNull String userKey, String name, String picture) {
            this.userKey = userKey;
            this.username = name;
            this.picture = picture;
        }

        public User toEntity() {
            return User.builder()
                    .userKey(userKey)
                    .username(username)
                    .picture(picture)
                    .build();
        }
    }
}
