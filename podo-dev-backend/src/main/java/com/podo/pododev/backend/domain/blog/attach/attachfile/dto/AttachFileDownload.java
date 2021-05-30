package com.podo.pododev.backend.domain.blog.attach.attachfile.dto;

import com.podo.pododev.backend.domain.blog.attach.attachfile.model.AttachFile;
import lombok.Getter;

@Getter
public class AttachFileDownload {

    private String filename;
    private String originFilename;
    private String uploadedUrl;
    private String filePath;
    private Long filesize;

    public AttachFileDownload(AttachFile file, String uploadedUrl) {
        this.filename = file.getFilename();
        this.originFilename = file.getOriginFilename();
        this.uploadedUrl = uploadedUrl;
        this.filePath = file.getFilePath();
        this.filesize = file.getFilesize();
    }

}
