package com.podo.pododev.storage.application;

import com.podo.pododev.storage.dto.FileDelete;
import com.podo.pododev.storage.dto.FileWrite;
import com.podo.pododev.storage.global.util.FileCRUDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileCRUDService {

    @Value("${storage.uploaded.dir:}")
    private String uploadedDirectory;

    public void writeFile(FileWrite write) {
        FileCRUDUtil.writeForceDirectory(uploadedDirectory + write.getPath());
        FileCRUDUtil.writeFile(uploadedDirectory + write.getPath() + "/" +write.getFile().getOriginalFilename(), write.getFile());
    }

    public void deleteFile(FileDelete delete) {
        FileCRUDUtil.deleteFile(uploadedDirectory + delete.getPath(), delete.getFilename());
    }
}
