package com.podo.pododev.uploader.domain.upload;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/uploader/file")
@RestController
public class UploadController {

    private final UploadService uploadService;

    /**
     * 파일 저장
     */
    @PostMapping
    public ApiResponse upload(@Validated @ModelAttribute UploadDto.insert insert) {
        log.info("File Upload '{}', '{}', '{}'", insert.getPath(), insert.getFile().getOriginalFilename(), insert.getFile().getSize());
        uploadService.save(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping
    public ApiResponse delete(@Validated @RequestBody UploadDto.delete delete) {
        log.info("File Delete '{}', '{}'", delete.getPath(), delete.getFilename());

        uploadService.delete(delete);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }
}
