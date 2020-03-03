package com.podo.pododev.web.global.config.security.oauth;

import java.util.Map;

public interface AttributesParser {

    OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes);
}
