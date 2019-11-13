package com.cglee079.pododev.web.domain.blog.attachimage;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ImageInfoVo {
    private Integer width;
    private Integer height;

    public ImageInfoVo(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }
}
