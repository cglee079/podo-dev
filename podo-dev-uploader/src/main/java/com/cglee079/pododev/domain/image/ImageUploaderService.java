package com.cglee079.pododev.domain.image;

import com.cglee079.pododev.global.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageUploaderService {

    @Value("${upload.image.dir.location}")
    private String dir;

    public ImageDto.response uploadImage(MultipartFile multipartFile) {
        if (UploadFileUtils.isImageFile(multipartFile)) {
            //TODO throw exception..
        }

        String originName = multipartFile.getOriginalFilename();
        String extension = UploadFileUtils.getExtension(originName);
        String filename = UploadFileUtils.makeRandFilename(extension);
        String saveDirPath = dir + UploadFileUtils.pathByDate();

        UploadFileUtils.forceMakeDir(saveDirPath);

        File file = UploadFileUtils.saveFile( saveDirPath + "/" + filename, multipartFile);
        BufferedImage image = getImage(file);

        long filesize = file.length();
        int width = image.getWidth();
        int height = image.getHeight();

        return ImageDto.response.builder()
                .filename(filename)
                .filesize(filesize)
                .width(width)
                .height(height)
                .build();

    }

    private BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
    }



}
