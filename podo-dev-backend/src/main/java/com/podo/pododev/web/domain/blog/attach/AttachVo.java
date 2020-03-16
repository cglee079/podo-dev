package com.podo.pododev.web.domain.blog.attach;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AttachVo {

    private String filePath;
    private String filename;

    public AttachVo(String filePath, String filename) {
        this.filePath = filePath;
        this.filename = filename;
    }
}
