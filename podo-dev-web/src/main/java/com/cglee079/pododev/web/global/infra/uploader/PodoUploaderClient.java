package com.cglee079.pododev.web.global.infra.uploader;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import sun.security.provider.certpath.OCSPResponse;

import java.io.File;

@Slf4j
@Component
public class PodoUploaderClient {

    @Value("${infra.uploader.token}")
    private String token;

    @Value("${infra.uploader.domain}")
    private String serverUrl;

    @Value("${infra.uploader.upload.subpath}")
    private String subpath;

    public void upload(String path, File file) {
        log.info("Upload Start '{}'", file.getPath() + "/" + file.getName());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("path", path);
        body.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + subpath, HttpMethod.POST, request, String.class);

        if(response.getStatusCode() != HttpStatus.OK){
            log.error("body : {}", response.getStatusCode());
            log.error("headers : {}", response.getHeaders());
            log.error("body : {}", response.getBody());
        }

        log.info("Upload Response '{}'", response.toString());
    }

    public void delete(String path, String filename) {
        log.info("Delete Start.... '{}'", path + "/" + filename);

        JSONObject object = new JSONObject();
        object.put("path", path);
        object.put("filename", filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<>(object.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + subpath, HttpMethod.DELETE, request, String.class);

        log.info("Delete Response '{}'", response.toString());

    }
}
