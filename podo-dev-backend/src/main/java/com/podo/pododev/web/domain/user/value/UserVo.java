package com.podo.pododev.web.domain.user.value;

import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.podo.pododev.web.global.config.security.role.UserRole.ADMIN;


@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class UserVo {

    private OAuthType oAuthType;
    private String username;
    private String picture;
    private Boolean isAdmin;

    public static UserVo createByUser(User user) {
        final UserVo userVo = new UserVo();
        userVo.oAuthType = user.getOAuthType();
        userVo.username = user.getUsername();
        userVo.picture = user.getPicture();
        userVo.isAdmin = ADMIN.equals(user.getRole());
        return userVo;
    }
}
