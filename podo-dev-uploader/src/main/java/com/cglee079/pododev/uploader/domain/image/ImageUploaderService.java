package com.cglee079.pododev.uploader.domain.image;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageUploaderService {

    @Value("${uploader.upload.base.dir}")
    private String dir;

    public void saveImage(ImageDto.insert insert) {
        MyFileUtils.makeForceDir(dir + insert.getPath());
        MyFileUtils.saveFile(dir + insert.getPath() + "/" +insert.getImage().getOriginalFilename(), insert.getImage());
    }

    public void deleteImage(ImageDto.delete delete) {
        MyFileUtils.deleteFile(dir + delete.getPath(), delete.getFilename());
    }
}
