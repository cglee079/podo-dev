package com.podo.pododev.web.job.maker.sitemap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SitemapSender {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendRequest(String url) {
        log.info("Sitemap Send, To '{}'", url);

        String response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class).getBody();

        log.info("Sitemap Send, Response '{}'", response.replace("\n", " "));
    }
}
