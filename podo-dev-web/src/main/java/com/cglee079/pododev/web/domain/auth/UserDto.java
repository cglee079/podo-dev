package com.cglee079.pododev.web.domain.auth;

import com.cglee079.pododev.web.global.config.security.UserRole;
import com.cglee079.pododev.web.global.config.security.oauth.GoogleUserDetails;
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
            this.googleIdentifier = authUser.getGoogleIdentifier();
            this.email = authUser.getEmail();
            this.name = authUser.getName();
            this.picture = authUser.getPicture();
            this.isAdmin = false;

            authUser.getAuthorities().forEach(auth -> {
                if (auth.getAuthority().equals("ROLE_" + UserRole.ADMIN)) {
                    isAdmin = true;
                    return;
                }
            });
        }

    }
}
