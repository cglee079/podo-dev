package com.podo.pododev.backend.domain.blog.attach.attachimage.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class AttachImageSave {

    private String imageKey;

    private String filename;

    private String filePath;

    private Integer width;

    private Integer height;

    private Long filesize;

    @Builder
    public AttachImageSave(String imageKey, String filename, String filePath, Integer width, Integer height, Long filesize) {
        this.imageKey = imageKey;
        this.filename = filename;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.filesize = filesize;
    }

}
