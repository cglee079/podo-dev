package com.podo.pododev.core.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MyStringUtil {

    public static String encodeFilenameByBrowser(String browser, String filename) {

        String encodedFilename;

        try {
            if (browser.equals("Opera")) {
                encodedFilename = "\"" + new String(filename.getBytes(StandardCharsets.UTF_8), "8859_1");
            } else if (browser.equals("Chrome")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < filename.length(); i++) {
                    char c = filename.charAt(i);
                    if (c > '~') {
                        sb.append(URLEncoder.encode("" + c, "UTF-8"));
                    } else {
                        sb.append(c);
                    }
                }
                encodedFilename = sb.toString();
            } else {
                encodedFilename = URLEncoder.encode(filename, "UTF-8").replace("+", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            encodedFilename = filename;
        }

        return encodedFilename;
    }

    private static String getBrowser(String header) {
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
