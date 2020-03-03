package com.podo.pododev.web.global.config.security.oauth.parser.util;

import com.podo.pododev.web.global.config.security.oauth.OAuthType;
import com.podo.pododev.web.global.util.HashUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParserUtil {

    public String encodeUserKey(OAuthType oAuthType, String id){
        return HashUtil.hash(oAuthType.name(), id);
    }
}
