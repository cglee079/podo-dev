package com.podo.pododev.web.global.infra.storage;

import com.podo.pododev.web.global.config.filter.ThreadLocalContext;
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
public class PodoStorageClient {

    @Value("${infra.storage.auth.token}")
    private String token;

    @Value("${infra.storage.server.internal}")
    private String serverUrl;

    public void uploadFile(String path, File file) {
        ThreadLocalContext.debug(String.format("Storage 서버에 '%s' 파일 업로드를 요청합니다", file.getPath() + "/" + file.getName()));

        final MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("path", path);
        requestBody.add("file", new FileSystemResource(file));

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        requestHeaders.setBearerAuth(token);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<String> response = new RestTemplate().exchange(serverUrl, HttpMethod.POST, request, String.class);

        ThreadLocalContext.debug(String.format("Storage 파일 업로드 요청 응답 : '%s'", response.toString()));
    }

    public void deleteFile(String directory, String filename) {
        ThreadLocalContext.debug(String.format("Storage 서버에 '%s' 파일 삭제를 요청합니다", directory + "/" + filename));

        final JSONObject requestBody = new JSONObject();
        requestBody.put("path", directory);
        requestBody.put("filename", filename);

        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(token);

        final HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), requestHeaders);

        final ResponseEntity<String> response = new RestTemplate().exchange(serverUrl, HttpMethod.DELETE, request, String.class);

        ThreadLocalContext.debug(String.format("Storage 파일 삭제 요청 응답 '%s'", response.toString()));

    }
}
