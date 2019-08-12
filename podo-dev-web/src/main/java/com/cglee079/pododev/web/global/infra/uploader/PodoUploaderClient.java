package com.cglee079.pododev.web.global.infra.uploader;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Component
public class PodoUploaderClient {

    @Value("${podo.uploader.domain}")
    private String serverUrl;

    @Value("${podo.uploader.upload.image.subpath}")
    private String imageSubpath;

    public void uploadImages(String path, File file) {
        log.info("Image Upload Start.... {}", file.getPath() + "/" + file.getName());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("path", path);
        body.add("image", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + imageSubpath, HttpMethod.POST, request, String.class);

        log.info("Image Upload Complete.. {}", response);
    }

    public void deleteImage(String path, String filename) {
        log.info("Image Delete Start.... {}", path + "/" + filename);

        JSONObject object = new JSONObject();
        object.put("path", path);
        object.put("filename", filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(object.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(serverUrl + imageSubpath, HttpMethod.DELETE, request, String.class);

        log.info("Image Delete Complete.. {}", response);

    }
}
