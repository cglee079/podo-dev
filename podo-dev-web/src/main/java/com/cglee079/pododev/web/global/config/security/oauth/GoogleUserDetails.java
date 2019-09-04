package com.cglee079.pododev.web.global.config.security.oauth;

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

    private String googleIdentifier;
    private String email;
    private String name;
    private String picture;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Builder
    public GoogleUserDetails(String googleIdentifier, String email, String name, String picture) {
        this.googleIdentifier = googleIdentifier;
        this.email = email;
        this.name = name;
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
        return this.email;
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
