package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.ImageInfo;
import lombok.Builder;
import lombok.Getter;

public class AttachImageSaveDto {

    @Getter
    public static class response {
        private Long id;
        private String filename;
        private String path;
        private Integer width;
        private Integer height;
        private Long filesize;

        @Builder
        public response(Long id, String filename, String path, ImageInfo imageInfo, Long filesize) {
            this.id = id;
            this.filename = filename;
            this.path = path;
            this.width = imageInfo.getWidth();
            this.height = imageInfo.getHeight();
            this.filesize = filesize;
        }

        public response(AttachImageSave save) {
            this.id = save.getId();
            this.filename = save.getFilename();
            this.path = save.getPath();
            this.width = save.getWidth();
            this.height = save.getHeight();
            this.filesize = save.getFilesize();
        }
    }

    @Getter
    public static class insert {
        private Long id;
        private String filename;
        private String path;
        private Integer width;
        private Integer height;
        private Long filesize;

        public AttachImageSave toEntity(String imageId) {
            return AttachImageSave.builder()
                    .imageId(imageId)
                    .filename(this.filename)
                    .filesize(this.filesize)
                    .path(this.path)
                    .width(this.width)
                    .height(this.height)
                    .build();
        }
    }
}
