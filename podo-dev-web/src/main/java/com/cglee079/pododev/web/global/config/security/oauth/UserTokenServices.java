package com.cglee079.pododev.web.global.config.security.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.Map;

public class UserTokenServices extends UserInfoTokenServices {
    public UserTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
        setAuthoritiesExtractor(new OAuth2AuthoritiesExtractor());
    }

    @Override
    protected Object getPrincipal(Map<String, Object> map) {
        return super.getPrincipal(map);
    }

    public static class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {
        @Override
        public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
            return AuthorityUtils.createAuthorityList("USER");
        }
    }
}
