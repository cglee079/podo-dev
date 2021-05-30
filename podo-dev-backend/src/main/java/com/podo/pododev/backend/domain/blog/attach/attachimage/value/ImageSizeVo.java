package com.podo.pododev.backend.domain.blog.attach.attachimage.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ImageSizeVo {
    private Integer width;
    private Integer height;

    public ImageSizeVo(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }
}
