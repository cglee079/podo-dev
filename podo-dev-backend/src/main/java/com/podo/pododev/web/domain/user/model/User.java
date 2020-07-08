package com.podo.pododev.web.domain.user.model;

import com.podo.pododev.web.domain.BaseTimeEntity;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.role.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_type")
    @Enumerated(EnumType.STRING)
    private OAuthType oAuthType;

    @Column(unique = true)
    private String userKey;

    private String username;

    private String picture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(OAuthType oAuthType, String username, String userKey, String picture, UserRole role) {
        this.oAuthType = oAuthType;
        this.userKey = userKey;
        this.username = username;
        this.picture = picture;
        this.role = role;
    }

    @AllArgsNotNull
    public void updateUserInfo(String username, String picture) {
        if (!this.role.equals(UserRole.ADMIN)) {
            this.username = username;
            this.picture = picture;
        }
    }

}
