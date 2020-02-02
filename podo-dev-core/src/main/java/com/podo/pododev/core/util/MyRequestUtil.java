package com.podo.pododev.core.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class MyRequestUtil {

    public static String getBrowser(HttpServletRequest request) {
        final String header = request.getHeader("User-Agent");

        String browser;
        if (header.contains("MSIE")) {
            browser = "MSIE";
        } else if (header.contains("Chrome")) {
            browser = "Chrome";
        } else if (header.contains("Opera")) {
            browser = "Opera";
        } else if (header.contains("Trident/7.0")) {
            browser = "Firefox";
        } else {
            browser = "Chrome";
        }

        return browser;
    }
}
