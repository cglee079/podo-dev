package com.cglee079.pododev.domain.image;

import lombok.Builder;
import lombok.Getter;

public class ImageDto {
    @Getter
    public static class response{
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
