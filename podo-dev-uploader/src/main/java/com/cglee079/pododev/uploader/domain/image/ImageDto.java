package com.cglee079.pododev.uploader.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

public class ImageDto {
    @Setter
    @Getter
    public static class insert implements Serializable {
        private String path;
        private MultipartFile image;
    }

    @Getter
    public static class response {
        private String filename;
        private Long filesize;
        private Integer width;
        private Integer height;

        @Builder
        public response(String filename, Long filesize, Integer width, Integer height) {
            this.filename = filename;
            this.filesize = filesize;
            this.width = width;
            this.height = height;
        }
    }
}
