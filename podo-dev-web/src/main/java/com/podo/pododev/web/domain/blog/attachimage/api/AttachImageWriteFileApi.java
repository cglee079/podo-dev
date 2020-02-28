package com.podo.pododev.web.domain.blog.attachimage.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.podo.pododev.web.domain.blog.attachimage.application.AttachImageWriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachImageWriteFileApi {

    private final AttachImageWriteFileService attachImageWriteFileService;

    @PostMapping(value = "/api/blogs/images", consumes = "multipart/form-data")
    public ApiResponse uploadImageByMultipartFile(@RequestParam("fileOfImage") MultipartFile image) {
        final AttachImageDto.response response = attachImageWriteFileService.saveByMultipartFile(image);

        return DataResponse.success()
                .result(response)
                .build();
    }

    @PostMapping(value = "/api/blogs/images", consumes = "application/json;charset=UTF-8")
    public ApiResponse uploadImageByBase64OrUrl(@RequestBody AttachImageDto.upload upload) {

        AttachImageDto.response attachImage = null;

        final String base64 = upload.getBase64();
        if (Objects.nonNull(base64)) {
            attachImage = attachImageWriteFileService.saveByBase64(base64);
        }

        else {
            final String imageUrl = upload.getImageUrl();
            if (Objects.nonNull(imageUrl)) {
                attachImage = attachImageWriteFileService.saveByImageUrl(imageUrl);
            }
        }

        return DataResponse.success()
                .result(attachImage)
                .build();


    }


}
