package com.podo.pododev.storage.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.core.rest.response.StatusResponse;
import com.podo.pododev.storage.dto.UploadDto;
import com.podo.pododev.storage.application.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class UploadApi {

    private final UploadService uploadService;

    @PostMapping("/api/file")
    public ApiResponse upload(@Validated @ModelAttribute UploadDto.insert insert) {
        log.info("'{}', '{}' 파일 저장 요청을 받았습니다. 파일크기 : '{}'", insert.getPath(), insert.getFile().getOriginalFilename(), insert.getFile().getSize());

        uploadService.writeFile(insert);

        return StatusResponse.success();
    }

    @DeleteMapping("/api/file")
    public ApiResponse delete(@Validated @RequestBody UploadDto.delete delete) {
        log.info("'{}', '{}' 파일 삭제 요청을 받았습니다.", delete.getPath(), delete.getFilename());

        uploadService.deleteFile(delete);

        return StatusResponse.success();
    }
}
