package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.BaseTimeEntity;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userKey;

    private String username;

    private String picture;

    @Builder
    public User(String username, String userKey, String picture) {
        this.userKey = userKey;
        this.username = username;
        this.picture = picture;
    }

    @AllArgsNotNull
    public void updateUserInfo(String username, String picture) {
        this.username = username;
        this.picture = picture;
    }

    public void setUserKey(String google) {
        this.userKey = google;
    }
}
