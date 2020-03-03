package com.podo.pododev.web.global.config.security.oauth.parser;

import com.podo.pododev.web.global.config.security.oauth.AttributesParser;
import com.podo.pododev.web.global.config.security.oauth.OAuthAttributes;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.config.security.oauth.parser.util.ParserUtil;

import java.util.Map;

public class FacebookAttributesParser implements AttributesParser {

    private static final String ID_KEY = "id";
    private static final String USERNAME_KEY = "name";
    private static final String PICTURE_FORMAT = "https://graph.facebook.com/%s/picture";

    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        final String id = (String) attributes.get(ID_KEY);
        final String userKey = ParserUtil.encodeUserKey(oAuthType, id);
        final String username = (String) attributes.get(USERNAME_KEY);
        final String picture = String.format(PICTURE_FORMAT, id);

        return OAuthAttributes.builder()
                .oAuthType(oAuthType)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();

    }
}
