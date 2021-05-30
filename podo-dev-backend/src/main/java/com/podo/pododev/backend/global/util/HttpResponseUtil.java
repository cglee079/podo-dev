package com.podo.pododev.backend.global.util;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class HttpResponseUtil {

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

}
