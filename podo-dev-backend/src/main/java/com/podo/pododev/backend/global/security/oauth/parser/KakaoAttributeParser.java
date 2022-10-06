package com.podo.pododev.backend.global.security.oauth.parser;

import com.podo.pododev.backend.global.security.oauth.AttributesParser;
import com.podo.pododev.backend.global.security.oauth.OAuthAttributes;
import com.podo.pododev.backend.global.security.oauth.OAuthType;
import com.podo.pododev.backend.global.security.oauth.parser.util.ParserUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

public class KakaoAttributeParser implements AttributesParser {

    private static final String ID_KEY = "id";
    private static final String USER_DETAIL_WRAP_KEY = "kakao_account";
    private static final String USER_DETAIL_KEY = "profile";
    private static final String USERNAME_KEY = "nickname";
    private static final String PICTURE_KEY = "profile_image_url";
    private static final String EMAIL_KEY = "email";
    private static final String DEFAULT_PICTURE = "https://www.podo-dev.com/user/default-kakao.png";

    @SuppressWarnings("unchecked")
    public OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes) {
        final Map<String, Object> userDetailWrap = (Map<String, Object>) attributes.get(USER_DETAIL_WRAP_KEY);
        final Map<String, String> userDetail = (Map<String, String>) userDetailWrap.get(USER_DETAIL_KEY);

        final String id = ((Integer) attributes.get(ID_KEY)).toString();
        final String userKey = ParserUtil.encodeUserKey(oAuthType, id);
        final String username = userDetail.get(USERNAME_KEY);
        final String picture = getPicture(userDetail.get(PICTURE_KEY), DEFAULT_PICTURE);
        final String email = (String) userDetailWrap.get(EMAIL_KEY);

        return OAuthAttributes.builder()
                .email(email)
                .oAuthType(oAuthType)
                .userKey(userKey)
                .username(username)
                .picture(picture)
                .build();
    }

    private String getPicture(String pictureUrl, String defaultPicture) {
        if(StringUtils.hasText(pictureUrl)){
            return pictureUrl;
        }

        return defaultPicture;
    }
}