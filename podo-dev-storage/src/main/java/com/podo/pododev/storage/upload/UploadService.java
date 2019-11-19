package com.podo.pododev.storage.upload;

import com.podo.pododev.core.util.MyFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Value("${uploader.upload.base.dir}")
    private String dir;

    public void save(UploadDto.insert insert) {
        MyFileUtils.makeForceDirectory(dir + insert.getPath());
        MyFileUtils.saveFile(dir + insert.getPath() + "/" +insert.getFile().getOriginalFilename(), insert.getFile());
    }

    public void delete(UploadDto.delete delete) {
        MyFileUtils.deleteFile(dir + delete.getPath(), delete.getFilename());
    }
}
