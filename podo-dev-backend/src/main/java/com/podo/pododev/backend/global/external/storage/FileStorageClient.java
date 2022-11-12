package com.podo.pododev.backend.global.external.storage;

import com.podo.pododev.backend.global.context.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Component
public class FileStorageClient {
    @Value("${external.storage.server.internal:}")
    private String serverUrl;

    RestTemplate restTemplate = new RestTemplate();

    public void uploadFile(String path, File file) {
        ThreadLocalContext.debug(String.format("Storage 서버에 '%s' 파일 업로드를 요청합니다", file.getPath()));

        final MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("namespace", "podo-dev");
        requestBody.add("path", path);
        requestBody.add("file", new FileSystemResource(file));

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/api/write", HttpMethod.POST, request, String.class);

        ThreadLocalContext.debug(String.format("Storage 파일 업로드 요청 응답 : '%s'", response));
    }

    public void deleteFile(String directory, String filename) {
        ThreadLocalContext.debug(String.format("Storage 서버에 '%s' 파일 삭제를 요청합니다", directory + "/" + filename));

        final JSONObject requestBody = new JSONObject();
        requestBody.put("namespace", "podo-dev");
        requestBody.put("path", directory);
        requestBody.put("filename", filename);

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), requestHeaders);

        final ResponseEntity<String> response = restTemplate.exchange(serverUrl + "/api/delete", HttpMethod.POST, request, String.class);

        ThreadLocalContext.debug(String.format("Storage 파일 삭제 요청 응답 '%s'", response.toString()));

    }
}
