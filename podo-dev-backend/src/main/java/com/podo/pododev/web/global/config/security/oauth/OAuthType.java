package com.podo.pododev.web.global.config.security.oauth;

import com.podo.pododev.web.global.config.security.oauth.exception.InvalidOAuthTypeException;
import com.podo.pododev.web.global.config.security.oauth.parser.FacebookAttributesParser;
import com.podo.pododev.web.global.config.security.oauth.parser.GithubAttributesParser;
import com.podo.pododev.web.global.config.security.oauth.parser.GoogleAttributesParser;
import com.podo.pododev.web.global.config.security.oauth.parser.KakaoAttributeParser;
import com.podo.pododev.web.global.config.security.oauth.parser.NaverAttrieButesParser;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum OAuthType {

    GOOGLE("google", new GoogleAttributesParser()),
    FACEBOOK("facebook", new FacebookAttributesParser()),
    KAKAO("kakao", new KakaoAttributeParser()),
    GITHUB("github", new GithubAttributesParser()),
    NAVER("naver", new NaverAttrieButesParser());

    private final String value;
    private final AttributesParser attributesParser;

    public OAuthAttributes createAttributes(Map<String, Object> attributes){
        return this.attributesParser.of(this, attributes);
    }

    public static OAuthType from(String value){
        for (OAuthType oAuthType : OAuthType.values()) {
            if(oAuthType.value.equalsIgnoreCase(value)){
                return oAuthType;
            }
        }

        throw new InvalidOAuthTypeException(value);
    }
}
