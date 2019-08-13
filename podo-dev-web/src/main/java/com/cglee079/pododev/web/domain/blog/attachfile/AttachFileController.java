package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ResponseStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AttachFileController {

    private final AttachFileService attachFileService;

    @PostMapping("/blogs/files")
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile file) {
        AttachFileDto.response response = attachFileService.saveFile(file);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(response)
                .build();
    }

}
