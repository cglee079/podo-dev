package com.podo.pododev.web.global.infra.storage;

import com.podo.pododev.web.global.infra.storage.exception.UploadFailException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Component
public class PodoStorageClient {

    @Value("${infra.storage.token}")
    private String token;

    @Value("${infra.storage.server.internal}")
    private String serverUrl;

    /**
     * Upload to Storage Server
     *
     * @param path
     * @param file
     */
    public void upload(String path, File file) {
        log.info("Upload Start '{}'", file.getPath() + "/" + file.getName());

        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("path", path);
        body.add("file", new FileSystemResource(file));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = new RestTemplate().exchange(serverUrl, HttpMethod.POST, request, String.class);
            log.info("Upload Response '{}'", response.toString());
        } catch (HttpClientErrorException e) {
            throw new UploadFailException(e.getMessage());
        }
    }

    /**
     * Delete to Uploader Server
     *
     * @param path
     * @param filename
     */
    public void delete(String path, String filename) {
        log.info("Delete Start.... '{}'", path + "/" + filename);

        final JSONObject object = new JSONObject();
        object.put("path", path);
        object.put("filename", filename);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        final HttpEntity<String> request = new HttpEntity<>(object.toString(), headers);

        final ResponseEntity<String> response = new RestTemplate().exchange(serverUrl, HttpMethod.DELETE, request, String.class);

        log.info("Delete Response '{}'", response.toString());

    }
}
