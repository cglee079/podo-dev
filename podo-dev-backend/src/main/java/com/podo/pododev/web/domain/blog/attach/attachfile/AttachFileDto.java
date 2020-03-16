package com.podo.pododev.web.domain.blog.attach.attachfile;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import lombok.Builder;
import lombok.Getter;

public class AttachFileDto {

    @Getter
    public static class response {
        private Long id;
        private String filename;
        private String originFilename;
        private String uploadedUrl;
        private String filePath;
        private Long filesize;
        private AttachStatus attachStatus;

        @Builder
        public response(Long id, String filename, String originFilename, String uploadedUrl, String filePath, Long filesize, AttachStatus attachStatus) {
            this.id = id;
            this.filename = filename;
            this.originFilename = originFilename;
            this.uploadedUrl = uploadedUrl;
            this.filePath = filePath;
            this.filesize = filesize;
            this.attachStatus = attachStatus;
        }

        public static AttachFileDto.response createByAttachFile(AttachFile file, String uploadedUrl, AttachStatus attachStatus) {
            return response.builder()
                    .id(file.getId())
                    .filePath(file.getFilePath())
                    .filename(file.getFilename())
                    .originFilename(file.getOriginFilename())
                    .uploadedUrl(uploadedUrl)
                    .filesize(file.getFilesize())
                    .attachStatus(attachStatus)
                    .build();
        }
    }


    @Getter
    public static class download {
        private String filename;
        private String originFilename;
        private String uploadedUrl;
        private String filePath;
        private Long filesize;

        public download(AttachFile file, String uploadedUrl) {
            this.filename = file.getFilename();
            this.originFilename = file.getOriginFilename();
            this.uploadedUrl = uploadedUrl;
            this.filePath = file.getFilePath();
            this.filesize = file.getFilesize();
        }
    }

    @Getter
    public static class insert {
        private Long id;
        private String filename;
        private String originFilename;
        private String filePath;
        private Long filesize;
        private AttachStatus attachStatus;

        public AttachFile toEntity() {
            return AttachFile.builder()
                    .filename(filename)
                    .originFilename(originFilename)
                    .filePath(filePath)
                    .filesize(filesize)
                    .build();
        }
    }

}
