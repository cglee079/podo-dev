package com.cglee079.pododev.web.global.config.security.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class GoogleUserDetails implements UserDetails {

    private String googleIdentifier;
    private String email;
    private String username;
    private String picture;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Builder
    public GoogleUserDetails(String googleIdentifier, String email, String username, String picture) {
        this.googleIdentifier = googleIdentifier;
        this.email = email;
        this.username = username;
        this.picture = picture;
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

    public void addAuthority(SimpleGrantedAuthority auth) {
        this.authorities.add(auth);
    }

}
