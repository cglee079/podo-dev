package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AttachImageController {

    private final AttachImageService attachImageService;

    @PostMapping("/blogs/images")
    public ApiResponse uploadImages(@RequestParam("image") MultipartFile image){
        AttachImageDto.response response = attachImageService.saveImage(image);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(response)
                .build();
    }

}
