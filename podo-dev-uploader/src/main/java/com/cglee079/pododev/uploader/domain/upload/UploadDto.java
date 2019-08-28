package com.cglee079.pododev.uploader.domain.upload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UploadDto {
    @Setter
    @Getter
    public static class insert implements Serializable {

        @NotEmpty(message = "PATH를 입력해주세요")
        private String path;

        @NotNull(message = "파일을 입력해주세요")
        private MultipartFile file;
    }

    @Getter
    public static class delete {
        @NotEmpty(message = "PATH를 입력해주세요")
        private String path;

        private String filename;
    }
}
