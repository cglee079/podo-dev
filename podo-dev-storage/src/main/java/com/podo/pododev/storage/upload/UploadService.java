package com.podo.pododev.storage.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Value("${storage.uploaded.dir}")
    private String uploadedDirectory;

    public void writeFile(UploadDto.insert insert) {
        MyFileUtils.writeForceDirectory(uploadedDirectory + insert.getPath());
        MyFileUtils.writeFile(uploadedDirectory + insert.getPath() + "/" +insert.getFile().getOriginalFilename(), insert.getFile());
    }

    public void deleteFile(UploadDto.delete delete) {
        MyFileUtils.deleteFile(uploadedDirectory + delete.getPath(), delete.getFilename());
    }
}
