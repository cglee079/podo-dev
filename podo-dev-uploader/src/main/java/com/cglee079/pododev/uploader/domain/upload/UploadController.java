package com.cglee079.pododev.uploader.domain.upload;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ResponseStatus;
import com.cglee079.pododev.core.global.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UploadController {

    private final UploadService uploadService;

    /**
     * 이미지 저장
     */
    @PostMapping("/upload")
    public ApiResponse upload(@ModelAttribute UploadDto.insert insert) {
       uploadService.save(insert);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }

    /**
     * 이미지 삭제
     */
    @DeleteMapping("/upload")
    public ApiResponse delete(@RequestBody UploadDto.delete delete) {
        uploadService.delete(delete);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }
}
