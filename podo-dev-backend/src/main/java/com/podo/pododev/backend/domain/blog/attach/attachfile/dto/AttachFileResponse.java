package com.podo.pododev.backend.domain.blog.attach.attachfile.dto;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.attachfile.model.AttachFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AttachFileResponse {

    private Long id;
    private String filename;
    private String originFilename;
    private String uploadedUrl;
    private String filePath;
    private Long filesize;
    private AttachStatus attachStatus;

    @Builder
    public AttachFileResponse(Long id, String filename, String originFilename, String uploadedUrl, String filePath, Long filesize, AttachStatus attachStatus) {
        this.id = id;
        this.filename = filename;
        this.originFilename = originFilename;
        this.uploadedUrl = uploadedUrl;
        this.filePath = filePath;
        this.filesize = filesize;
        this.attachStatus = attachStatus;
    }

    public static AttachFileResponse createByAttachFile(AttachFile file, String uploadedUrl, AttachStatus attachStatus) {
        return AttachFileResponse.builder()
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
