package com.podo.pododev.backend.domain.blog.attach.attachimage.api;

import com.podo.pododev.backend.domain.blog.attach.attachimage.application.AttachImageWriteFileService;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageResponse;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachImageWriteFileApi {

    private final AttachImageWriteFileService attachImageWriteFileService;

    @PostMapping(value = "/api/blogs/images", consumes = "multipart/form-data")
    public AttachImageResponse uploadImageByMultipartFile(@RequestParam("fileOfImage") MultipartFile image) {
        return attachImageWriteFileService.saveByMultipartFile(image);
    }

    @PostMapping(value = "/api/blogs/images", consumes = "application/json;charset=UTF-8")
    public AttachImageResponse uploadImageByBase64OrUrl(@RequestBody AttachImageUpload upload) {

        final String base64 = upload.getBase64();
        if (Objects.nonNull(base64)) {
            return attachImageWriteFileService.saveByBase64(base64);
        }

        final String imageUrl = upload.getImageUrl();
        if (Objects.nonNull(imageUrl)) {
            return attachImageWriteFileService.saveByImageUrl(imageUrl);
        }

        return null; // TODO
    }

}
