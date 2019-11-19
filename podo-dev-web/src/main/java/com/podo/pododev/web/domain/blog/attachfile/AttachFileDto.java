package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.domain.blog.AttachStatus;
import lombok.Builder;
import lombok.Getter;

public class AttachFileDto {

    @Getter
    public static class response {
        private Long id;
        private String filename;
        private String originName;
        private String uploadedUrl;
        private String path;
        private Long filesize;
        private AttachStatus attachStatus;

        @Builder
        public response(Long id, String filename, String originName, String uploadedUrl, String path, Long filesize, AttachStatus attachStatus) {
            this.id = id;
            this.filename = filename;
            this.originName = originName;
            this.uploadedUrl = uploadedUrl;
            this.path = path;
            this.filesize = filesize;
            this.attachStatus = attachStatus;
        }

        public static AttachFileDto.response createByAttachFile(AttachFile file, String uploadedUrl, AttachStatus attachStatus) {
            return response.builder()
                    .id(file.getId())
                    .path(file.getPath())
                    .filename(file.getFilename())
                    .originName(file.getOriginName())
                    .uploadedUrl(uploadedUrl)
                    .filesize(file.getFilesize())
                    .attachStatus(attachStatus)
                    .build();
        }
    }


    @Getter
    public static class download {
        private String filename;
        private String originName;
        private String uploadedUrl;
        private String path;
        private Long filesize;

        public download(AttachFile file, String uploadedUrl) {
            this.filename = file.getFilename();
            this.originName = file.getOriginName();
            this.uploadedUrl = uploadedUrl;
            this.path = file.getPath();
            this.filesize = file.getFilesize();
        }
    }

    @Getter
    public static class insert {
        private Long id;
        private String filename;
        private String originName;
        private String path;
        private Long filesize;
        private AttachStatus attachStatus;

        public AttachFile toEntity() {
            return AttachFile.builder()
                    .filename(filename)
                    .originName(originName)
                    .path(path)
                    .filesize(filesize)
                    .build();

        }
    }

}
