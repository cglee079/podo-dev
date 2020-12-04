package com.podo.pododev.web.domain.blog.attach.attachfile.dto;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import com.podo.pododev.web.domain.blog.attach.attachfile.model.AttachFile;
import lombok.Getter;

@Getter
public class AttachFileInsert {

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
