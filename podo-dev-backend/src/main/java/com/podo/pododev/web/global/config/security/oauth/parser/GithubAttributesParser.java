package com.podo.pododev.web.global.config.security.oauth.parser;

import com.podo.pododev.web.global.config.security.oauth.AttributesParser;
import com.podo.pododev.web.global.config.security.oauth.OAuthAttributes;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.oauth.parser.util.ParserUtil;

import java.util.Map;

public class GithubAttributesParser implements AttributesParser {

    private static final String USERNAME_KEY = "login";
    private static final String ID_KEY = "id";
    private static final String PICTURE_KEY = "avatar_url";

    @Override
    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        final String picture = (String) attributes.get(PICTURE_KEY);
        final String id = ((Integer) attributes.get(ID_KEY)).toString();
        final String username = (String) attributes.get(USERNAME_KEY);

        final String userKey = ParserUtil.encodeUserKey(oAuthType, id);

        return OAuthAttributes.builder()
                .oAuthType(oAuthType)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();
    }
}
