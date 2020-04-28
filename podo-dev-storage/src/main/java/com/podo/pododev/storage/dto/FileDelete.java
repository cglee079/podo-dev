package com.podo.pododev.storage.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class FileDelete {

    @NotEmpty(message = "PATH를 입력해주세요")
    private String path;

    private String filename;

}
