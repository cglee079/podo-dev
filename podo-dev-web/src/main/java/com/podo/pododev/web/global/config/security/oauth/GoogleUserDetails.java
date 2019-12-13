package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.global.config.security.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class GoogleUserDetails implements UserDetails {

    private String googleId;
    private String email;
    private String username;
    private String profileImage;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Builder
    public GoogleUserDetails(String googleId, String email, String username, String profileImage) {
        this.googleId = googleId;
        this.email = email;
        this.username = username;
        this.profileImage = profileImage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAuth(List<String> adminIds) {
        this.authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));

        if (adminIds.contains(googleId)) {
            this.authorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN));
        }
    }
}
