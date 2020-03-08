package com.podo.pododev.web.global.infra.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class RestApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String exchange(String url, HttpMethod method, Map<String, List<String>> header) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        for (String key : header.keySet()) {
            httpHeaders.put(key, header.get(key));
        }

        try {
            return restTemplate.exchange(url, method, new HttpEntity<>(httpHeaders), String.class).getBody();
        } catch (Exception e) {
            log.error("{} Api 요청에 실패했습니다", url, e);
            return null;
        }

    }
}
