package com.podo.pododev.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;


@UtilityClass
public class HttpUrlUtil {



    /**
     * BaseURL 추출
     * ex) http://www.youtube.com/watch~~
     * return http://www.youtube.com
     */
    public static String getBaseUrl(String urlStr) {

        try {
            final URL url = new URL(urlStr);
            String baseUrl = url.getProtocol() + "://" + url.getHost();

            if (url.getPort() != -1) {
                baseUrl += ":" + url.getPort();
            }

            return baseUrl;
        } catch (MalformedURLException e) {
            return "";
        }
    }
}
