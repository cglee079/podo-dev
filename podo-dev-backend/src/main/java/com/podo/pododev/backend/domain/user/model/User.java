package com.podo.pododev.backend.domain.user.model;

import com.podo.pododev.backend.domain.BaseTimeEntity;
import com.podo.pododev.backend.global.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.role.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    private String email;

    private String username;

    private String picture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(OAuthType oAuthType, String email, String username, String userKey, String picture, UserRole role) {
        this.oAuthType = oAuthType;
        this.email = email;
        this.userKey = userKey;
        this.username = username;
        this.picture = picture;
        this.role = role;
    }

    @AllArgsNotNull
    public void updateUserInfo(String username, String picture, String email) {
        if (!this.role.equals(UserRole.ADMIN)) {
            this.username = username;
            this.picture = picture;
            this.email = email;
        }
    }

}
