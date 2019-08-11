package com.cglee079.pododev.uploader.domain.image;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploaderService {

    @Value("${uploader.upload.base.dir}")
    private String dir;

    public void saveImage(ImageDto.insert insert) {
        File saved = new File(dir + insert.getPath(), insert.getImage().getOriginalFilename());
        try {
            FileUtils.forceMkdir(saved.getParentFile());
            insert.getImage().transferTo(saved);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }

    }
}
