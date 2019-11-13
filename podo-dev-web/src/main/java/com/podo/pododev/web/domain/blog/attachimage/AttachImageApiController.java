package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachImageApiController {

    private final AttachImageService attachImageService;

    @PostMapping(value = "/api/blogs/images", consumes = "multipart/form-data")
    public ApiResponse uploadImages(@RequestParam("image") MultipartFile image) {
        AttachImageDto.response response = attachImageService.saveImage(image);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(response)
                .build();
    }

    @PostMapping(value = "/api/blogs/images", consumes = "application/json;charset=UTF-8")
    public ApiResponse uploadBase64(@RequestBody AttachImageDto.upload upload) {
        AttachImageDto.response response = null;

        //Base64 Upload
        if (!Objects.isNull(upload.getBase64())) {
            response = attachImageService.saveBase64(upload.getBase64());
        }

        //Url Upload
        else if (!Objects.isNull(upload.getUrl())) {
            response = attachImageService.saveImageUrl(upload.getUrl());
        }


        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(response)
                .build();
    }


}
