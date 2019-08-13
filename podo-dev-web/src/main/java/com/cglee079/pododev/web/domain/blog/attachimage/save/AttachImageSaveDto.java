package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.web.domain.blog.attachimage.ImageInfo;
import lombok.Builder;
import lombok.Getter;

public class AttachImageSaveDto {

    @Getter
    public static class response {
        private Long seq;
        private String filename;
        private String path;
        private Integer width;
        private Integer height;
        private Long filesize;

        @Builder
        public response(Long seq, String filename, String path, ImageInfo imageInfo, Long filesize) {
            this.seq = seq;
            this.filename = filename;
            this.path = path;
            this.width = imageInfo.getWidth();
            this.height = imageInfo.getHeight();
            this.filesize = filesize;
        }

        public response(AttachImageSave save) {
            this.seq = save.getSeq();
            this.filename = save.getFilename();
            this.path = save.getPath();
            this.width = save.getWidth();
            this.height = save.getHeight();
            this.filesize = save.getFilesize();
        }
    }

    @Getter
    public static class insert {
        private String filename;
        private String path;
        private Integer width;
        private Integer height;
        private Long filesize;

        public AttachImageSave toEntity(String imageId) {
            return AttachImageSave.builder()
                    .filename(this.filename)
                    .filesize(this.filesize)
                    .imageId(imageId)
                    .path(this.path)
                    .width(this.width)
                    .height(this.height)
                    .build();
        }
    }

    @Getter
    public static class update {
        private Long seq;
        private String filename;
        private String path;
        private Integer width;
        private Integer height;
        private Long filesize;

        public AttachImageSave toEntity(String imageId) {
            return AttachImageSave.builder()
                    .filename(this.filename)
                    .filesize(this.filesize)
                    .imageId(imageId)
                    .path(this.path)
                    .width(this.width)
                    .height(this.height)
                    .build();
        }
    }
}
