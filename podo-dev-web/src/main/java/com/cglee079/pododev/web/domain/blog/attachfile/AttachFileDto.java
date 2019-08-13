package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import lombok.Builder;
import lombok.Getter;

public class AttachFileDto {

    @Getter
    public static class response {
        private Long seq;
        private String originKey;
        private String filename;
        private String originName;
        private String domainUrl;
        private String path;
        private Long filesize;
        private FileStatus fileStatus;

        @Builder
        public response(Long seq, String originKey, String filename, String originName, String domainUrl, String path, Long filesize, FileStatus fileStatus) {
            this.seq = seq;
            this.originKey = originKey;
            this.filename = filename;
            this.originName = originName;
            this.domainUrl = domainUrl;
            this.path = path;
            this.filesize = filesize;
            this.fileStatus = fileStatus;
        }
    }

    @Getter
    public static class insert {
        private String originKey;
        private String filename;
        private String originName;
        private String domainUrl;
        private String path;
        private Long filesize;
        private String fileStatus;

        public AttachFile toEntity() {
            return AttachFile.builder()
                    .originKey(originKey)
                    .filename(filename)
                    .originName(originName)
                    .path(path)
                    .filesize(filesize)
                    .build();

        }
    }
}
