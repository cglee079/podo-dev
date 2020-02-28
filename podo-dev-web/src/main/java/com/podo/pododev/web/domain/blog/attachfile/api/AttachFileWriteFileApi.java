package com.podo.pododev.web.domain.blog.attachfile.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachfile.application.AttachFileWriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachFileWriteFileApi {

    private final AttachFileWriteFileService attachFileWriteFileService;

    @PostMapping("/api/blogs/files")
    public ApiResponse uploadFileByMultipartFile(@RequestParam("file") MultipartFile file) {
        final AttachFileDto.response response = attachFileWriteFileService.saveFileFromMultipartFile(file);

        return DataResponse.success()
                .result(response)
                .build();
    }

}
