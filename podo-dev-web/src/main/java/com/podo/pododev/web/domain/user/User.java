package com.podo.pododev.web.domain.user;

import com.podo.pododev.web.domain.UpdatableBaseEntity;
import com.podo.pododev.web.global.aop.AllArgsNotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends UpdatableBaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String username;

    private String picture;

    @Builder
    public User(String username, String userId, String picture) {
        this.userId = userId;
        this.username = username;
        this.picture = picture;
    }

    @AllArgsNotNull
    public void updateUserInfo(String username, String picture) {
        this.username = username;
        this.picture = picture;
    }
}
