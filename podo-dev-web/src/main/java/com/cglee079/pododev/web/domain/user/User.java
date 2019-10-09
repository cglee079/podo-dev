package com.cglee079.pododev.web.domain.user;

import com.cglee079.pododev.web.domain.UpdatableBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends UpdatableBaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(unique = true)
    private String userId;

    private String username;

    private String email;

    private String picture;


    @Builder
    public User(String username, String userId, String email, String picture) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.picture = picture;
    }

    public boolean isChanged(String username, String email, String picture) {
        return !(this.username.equals(username)
                && this.email.equals(email)
                && this.picture.equals(picture));

    }

    public void updateInfo(String username, String email, String picture) {
        this.username = username;
        this.email = email;
        this.picture = picture;
    }
}
