package com.cglee079.pododev.uploader.domain.upload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadDto {
    @Setter
    @Getter
    public static class insert implements Serializable {
        private String path;
        private MultipartFile file;
    }

    @Getter
    public static class delete {
        private String path;
        private String filename;
    }
}
