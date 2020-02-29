package com.podo.pododev.storage.application;

import com.podo.pododev.storage.dto.UploadDto;
import com.podo.pododev.storage.global.util.FileCrudUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Value("${storage.uploaded.dir}")
    private String uploadedDirectory;

    public void writeFile(UploadDto.insert insert) {
        FileCrudUtil.writeForceDirectory(uploadedDirectory + insert.getPath());
        FileCrudUtil.writeFile(uploadedDirectory + insert.getPath() + "/" +insert.getFile().getOriginalFilename(), insert.getFile());
    }

    public void deleteFile(UploadDto.delete delete) {
        FileCrudUtil.deleteFile(uploadedDirectory + delete.getPath(), delete.getFilename());
    }
}
