package com.podo.pododev.web.global.config.security.oauth.parser;

import com.podo.pododev.web.global.config.security.oauth.AttributesParser;
import com.podo.pododev.web.global.config.security.oauth.OAuthAttributes;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.oauth.parser.util.ParserUtil;

import java.util.Map;

public class GoogleAttributesParser implements AttributesParser {

    private static final String USERNAME_KEY = "name";
    private static final String ID_KEY = "sub";
    private static final String PICTURE_KEY = "picture";
    private static final String EMAIL_KEY = "email";

    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        final String id = (String) attributes.get(ID_KEY);
        final String userKey = ParserUtil.encodeUserKey(oAuthType, id);
        final String picture = (String) attributes.get(PICTURE_KEY);
        final String username = (String) attributes.get(USERNAME_KEY);
        final String email = (String) attributes.get(EMAIL_KEY);

        return OAuthAttributes.builder()
                .oAuthType(oAuthType)
                .email(email)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();
    }
}
