package com.cglee079.pododev.uploader.domain.image;

import com.cglee079.pododev.web.core.global.response.ApiResponse;
import com.cglee079.pododev.web.core.global.response.DataResponse;
import com.cglee079.pododev.web.core.global.response.ResponseStatus;
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
}
