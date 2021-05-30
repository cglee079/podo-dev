package com.podo.pododev.backend.global.security.oauth.parser;

import com.podo.pododev.backend.global.security.oauth.AttributesParser;
import com.podo.pododev.backend.global.security.oauth.OAuthAttributes;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.oauth.parser.util.ParserUtil;

import java.util.Map;

public class GithubAttributesParser implements AttributesParser {

    private static final String USERNAME_KEY = "login";
    private static final String ID_KEY = "id";
    private static final String PICTURE_KEY = "avatar_url";
    private static final String EMAIL_KEY = "email";

    @Override
    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        final String picture = (String) attributes.get(PICTURE_KEY);
        final String id = ((Integer) attributes.get(ID_KEY)).toString();
        final String username = (String) attributes.get(USERNAME_KEY);
        final String email = (String) attributes.get(EMAIL_KEY);

        final String userKey = ParserUtil.encodeUserKey(oAuthType, id);

        return OAuthAttributes.builder()
                .email(email)
                .oAuthType(oAuthType)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();
    }
}
