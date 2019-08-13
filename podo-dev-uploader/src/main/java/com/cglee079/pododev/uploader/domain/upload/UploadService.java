package com.cglee079.pododev.uploader.domain.upload;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Value("${uploader.upload.base.dir}")
    private String dir;

    public void save(UploadDto.insert insert) {
        MyFileUtils.makeForceDir(dir + insert.getPath());
        MyFileUtils.saveFile(dir + insert.getPath() + "/" +insert.getFile().getOriginalFilename(), insert.getFile());
    }

    public void delete(UploadDto.delete delete) {
        MyFileUtils.deleteFile(dir + delete.getPath(), delete.getFilename());
    }
}
