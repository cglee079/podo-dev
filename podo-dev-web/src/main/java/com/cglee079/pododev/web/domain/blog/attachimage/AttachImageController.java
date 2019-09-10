package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AttachImageController {

    private final AttachImageService attachImageService;

    @PostMapping(value = "/blogs/images", consumes = "multipart/form-data")
    public ApiResponse uploadImages(@RequestParam("image") MultipartFile image) {
        AttachImageDto.response response = attachImageService.saveImage(image);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(response)
                .build();
    }

    @PostMapping(value = "blogs/images", consumes = "application/json;charset=UTF-8")
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
                .data(response)
                .build();
    }


}
