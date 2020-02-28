package com.podo.pododev.storage.application;

import com.podo.pododev.storage.dto.UploadDto;
import com.podo.pododev.web.global.util.MyFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Value("${storage.uploaded.dir}")
    private String uploadedDirectory;

    public void writeFile(UploadDto.insert insert) {
        MyFileUtil.writeForceDirectory(uploadedDirectory + insert.getPath());
        MyFileUtil.writeFile(uploadedDirectory + insert.getPath() + "/" +insert.getFile().getOriginalFilename(), insert.getFile());
    }

    public void deleteFile(UploadDto.delete delete) {
        MyFileUtil.deleteFile(uploadedDirectory + delete.getPath(), delete.getFilename());
    }
}
