package com.cglee079.pododev.web.global.util;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 로컬에 이미지 저장
 */
@RequiredArgsConstructor
@Component
public class FileWriter {

    @Value("${upload.base.dir}")
    private String baseDir;

    public File saveFile(String path, MultipartFile multipartFile) {
        String originName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originName);

        String dirPath = baseDir + path; // 로컬 저장경로

        //Save Origin File
        String filename = MyFileUtils.makeRandFilename(extension);
        File file = MyFileUtils.saveFile(dirPath + "/" + filename, multipartFile);

        return file;
    }

    public File resizeImage(File originImage, String path, Integer resizeWidth) {
        String originName = originImage.getName();
        String extension = FilenameUtils.getExtension(originName);

        if (extension.equalsIgnoreCase("gif")
                || extension.equalsIgnoreCase("jfif")) {
            extension = "jpg";
        }

        File resizeFile = new File(baseDir + path, MyFileUtils.makeRandFilename(extension));

        try {
            Thumbnails.of(originImage).width(resizeWidth).toFile(resizeFile);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }

        return resizeFile;

    }
}
