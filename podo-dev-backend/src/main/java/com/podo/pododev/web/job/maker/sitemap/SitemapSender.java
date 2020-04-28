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

    public void requestSubmitSitemap(String submitUrl) {
        log.debug("Sitemap 제출을 요청합니다 >>>  '{}'", submitUrl);

        final String response = restTemplate.exchange(submitUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class).getBody();

        log.debug("Sitemap 제출을 요청 응답 <<< '{}'", response.replace("\n", " "));
    }
}
