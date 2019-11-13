package com.podo.pododev.web.domain.blog.attachimage.save;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class AttachImageSave {

    private String imageId;

    private String filename;

    private String path;

    private Integer width;

    private Integer height;

    private Long filesize;

    @Builder
    public AttachImageSave(String imageId, String filename, String path, Integer width, Integer height, Long filesize) {
        this.filename = filename;
        this.imageId = imageId;
        this.path = path;
        this.width = width;
        this.height = height;
        this.filesize = filesize;
    }

}
