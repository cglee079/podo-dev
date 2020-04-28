package com.podo.pododev.web.domain.blog.attach.attachfile.api;

import com.podo.pododev.web.domain.blog.attach.attachfile.dto.AttachFileResponse;
import com.podo.pododev.web.domain.blog.attach.attachfile.application.AttachFileWriteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AttachFileWriteFileApi {

    private final AttachFileWriteFileService attachFileWriteFileService;

    @PostMapping("/api/blogs/files")
    public AttachFileResponse uploadFileByMultipartFile(@RequestParam("file") MultipartFile file) {
        return attachFileWriteFileService.saveFileFromMultipartFile(file);
    }

}
