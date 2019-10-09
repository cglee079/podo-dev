package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.blog.FileStatus;
import lombok.Builder;
import lombok.Getter;

public class AttachFileDto {

    @Getter
    public static class response {
        private Long seq;
        private String filename;
        private String originName;
        private String uploadedUrl;
        private String path;
        private Long filesize;
        private FileStatus fileStatus;

        @Builder
        public response(Long seq, String filename, String originName, String uploadedUrl, String path, Long filesize, FileStatus fileStatus) {
            this.seq = seq;
            this.filename = filename;
            this.originName = originName;
            this.uploadedUrl = uploadedUrl;
            this.path = path;
            this.filesize = filesize;
            this.fileStatus = fileStatus;
        }

        public response(AttachFile file, String uploadedUrl, FileStatus fileStatus) {
            this.seq = file.getSeq();
            this.filename = file.getFilename();
            this.originName = file.getOriginName();
            this.uploadedUrl = uploadedUrl;
            this.path = file.getPath();
            this.filesize = file.getFilesize();
            this.fileStatus = fileStatus;
        }
    }


    @Getter
    public static class download {
        private Long seq;
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
        private Long seq;
        private String filename;
        private String originName;
        private String path;
        private Long filesize;
        private String fileStatus;

        public AttachFile toEntity(Blog blog) {
            return AttachFile.builder()
                    .blog(blog)
                    .filename(filename)
                    .originName(originName)
                    .path(path)
                    .filesize(filesize)
                    .build();

        }
    }

}
