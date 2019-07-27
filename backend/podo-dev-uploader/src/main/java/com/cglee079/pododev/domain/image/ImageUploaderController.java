package com.cglee079.pododev.domain.image;

import com.cglee079.pododev.domain.api.ApiResponse;
import com.cglee079.pododev.domain.api.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ImageUploaderController {

    private final ImageUploaderService imageUploaderService;


    /**
     * 이미지 저장
     */
    @PostMapping("/images")
    public ApiResponse uploadImage(@RequestParam("thumbnail") MultipartFile multipartFile){
        ImageDto.response response = imageUploaderService.uploadImage(multipartFile);

        return ApiResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(response)
                .build();
    }
}
