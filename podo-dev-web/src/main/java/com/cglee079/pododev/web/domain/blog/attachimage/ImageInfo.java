package com.cglee079.pododev.web.domain.blog.attachimage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageInfo {
    private Integer width;
    private Integer height;

    @Builder
    public ImageInfo(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }
}
