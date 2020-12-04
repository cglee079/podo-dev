package com.podo.pododev.storage.api;

import com.podo.pododev.storage.application.FileCRUDService;
import com.podo.pododev.storage.dto.FileDelete;
import com.podo.pododev.storage.dto.FileWrite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class FileCRUDApi {

    private final FileCRUDService fileCRUDService;

    @PostMapping("/api/file")
    public void upload(@Valid @ModelAttribute FileWrite write) {
        log.debug("'{}', '{}' 파일 저장 요청을 받았습니다. 파일크기 : '{}'", write.getPath(), write.getFile().getOriginalFilename(), write.getFile().getSize());

        fileCRUDService.writeFile(write);
    }

    @DeleteMapping("/api/file")
    public void delete(@Valid @RequestBody FileDelete delete) {
        log.debug("'{}', '{}' 파일 삭제 요청을 받았습니다.", delete.getPath(), delete.getFilename());

        fileCRUDService.deleteFile(delete);
    }
}
