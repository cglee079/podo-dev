package com.podo.pododev.backend.domain.blog.attach;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AttachVO {

    private String filePath;
    private String filename;

    public AttachVO(String filePath, String filename) {
        this.filePath = filePath;
        this.filename = filename;
    }
}
