package com.podo.pododev.web.global.util;

import com.podo.pododev.web.global.util.exception.FileProcessFailApiException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@UtilityClass
public class ResourceFileUtil {

    public static String readResourceFile(String path) {
        try {
            final InputStream inputStream = new ClassPathResource(path).getInputStream();
            final byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("리소스 읽기 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }
    }

}
