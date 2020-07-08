package com.podo.pododev.storage.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FileWrite {

    @NotEmpty(message = "PATH를 입력해주세요")
    private String path;

    @NotNull(message = "파일을 입력해주세요")
    private MultipartFile file;
}
