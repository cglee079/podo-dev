package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachFileApiController {

    private final AttachFileService attachFileService;

    @PostMapping("/api/blogs/files")
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file) {
        AttachFileDto.response response = attachFileService.saveFile(file);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(response)
                .build();
    }

}
