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
    public ApiResponse uploadImageByMultipartFile(@RequestParam("fileOfImage") MultipartFile image) {
        final AttachImageDto.response response = attachImageService.saveByMultipartFile(image);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(response)
                .build();
    }

    @PostMapping(value = "/api/blogs/images", consumes = "application/json;charset=UTF-8")
    public ApiResponse uploadImageByBase64OrUrl(@RequestBody AttachImageDto.upload upload) {

        AttachImageDto.response attachImage = null;

        //Base64 Upload
        final String base64 = upload.getBase64();
        if (Objects.nonNull(base64)) {
            attachImage = attachImageService.saveByBase64(base64);
        }

        //Url Upload
        else {
            final String imageUrl = upload.getImageUrl();
            if (Objects.nonNull(imageUrl)) {
                attachImage = attachImageService.saveByImageUrl(imageUrl);
            }
        }

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(attachImage)
                .build();


    }


}
