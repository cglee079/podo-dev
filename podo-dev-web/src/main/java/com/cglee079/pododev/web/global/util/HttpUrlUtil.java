package com.cglee079.pododev.web.global.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlUtil {

    public static String getSeverDomain() {
        String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        return getBaseUrl(url);
    }

    /**
     * BaseURL 추출
     *
     * @param urlStr ex) http://www.youtube.com/watch~~
     * @return ex) http://www.youtube.com
     */
    public static String getBaseUrl(String urlStr) {

        try {
            URL url = new URL(urlStr);
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
