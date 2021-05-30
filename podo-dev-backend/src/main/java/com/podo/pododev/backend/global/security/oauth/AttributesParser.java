package com.podo.pododev.backend.global.security.oauth;

import java.util.Map;

public interface AttributesParser {

    OAuthAttributes of(OAuthType oAuthType, Map<String, Object> attributes);
}
