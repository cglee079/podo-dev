package com.podo.pododev.web.domain.blog.attach;

import lombok.Getter;

@Getter
public class AttachVo {

    private String filePath;
    private String filename;

    public AttachVo(String filePath, String filename) {
        this.filePath = filePath;
        this.filename = filename;
    }
}
