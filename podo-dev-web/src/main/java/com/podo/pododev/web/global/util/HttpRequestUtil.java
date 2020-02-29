package com.podo.pododev.web.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpRequestUtil {

    public static String getBrowser(String userAgent) {
        if (userAgent.contains("MSIE")) {
            return "MSIE";
        }

        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }

        if (userAgent.contains("Opera")) {
            return "Opera";
        }

        if (userAgent.contains("Trident/7.0")) {
            return "Firefox";
        }

        return "Chrome";
    }
}
