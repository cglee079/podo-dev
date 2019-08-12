package com.cglee079.pododev.uploader.domain.image;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ImageUploaderController {

    private final ImageUploaderService imageUploaderService;


    /**
     * 이미지 저장
     */
    @PostMapping("/images")
    public ApiResponse uploadImage(@ModelAttribute ImageDto.insert insert) {
       imageUploaderService.saveImage(insert);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }

    /**
     * 이미지 삭제
     */
    @DeleteMapping("/images")
    public ApiResponse deleteImage(@RequestBody ImageDto.delete delete) {
        imageUploaderService.deleteImage(delete);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }
}
