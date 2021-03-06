package com.podo.pododev.web.global.config.security.oauth.parser;

import com.podo.pododev.web.global.config.security.oauth.AttributesParser;
import com.podo.pododev.web.global.config.security.oauth.OAuthAttributes;
import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.util.HashUtil;

import java.util.Map;

public class NaverAttrieButesParser implements AttributesParser {

    private static final String USER_DETAIL_KEY = "response";
    private static final String ID_KEY = "id";
    private static final String USERNAME_KEY = "name";
    private static final String PICTURE_KEY = "profile_image";

    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        @SuppressWarnings("unchecked")
        final Map<String, String> response = (Map<String, String>) attributes.get(USER_DETAIL_KEY);

        final String id = response.get(ID_KEY);
        final String userKey = HashUtil.hash(oAuthType.name(), id);
        final String username = response.get(USERNAME_KEY);
        final String picture = response.get(PICTURE_KEY);

        return OAuthAttributes.builder()
                .oAuthType(oAuthType)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();
    }
}
