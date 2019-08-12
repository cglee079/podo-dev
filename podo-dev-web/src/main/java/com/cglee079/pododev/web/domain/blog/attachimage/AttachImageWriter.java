package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSavedDto;
import com.cglee079.pododev.core.global.util.MyFileUtils;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 로컬에 이미지 저장
 */
@RequiredArgsConstructor
@Component
public class AttachImageWriter {

    @Value("${upload.base.dir}")
    private String baseDir;

    @Value("${upload.postfix.image.dir}")
    private String imageDir;

    public Map<String, AttachImageSavedDto.response> makeSaveFile(MultipartFile multipartFile, String extension) {
        if (!this.isImageFile(multipartFile)) {
            //TODO throw exception..
            return null;
        }

        String dayDirPath = MyFileUtils.makeDatePath();
        String dirPath = dayDirPath + imageDir; // 로컬 저장경로
        String urlPath = dayDirPath + imageDir; // URL 경로

        File localDir = MyFileUtils.makeForceDir(baseDir + dirPath);

        Map<String, AttachImageSavedDto.response> saves = new HashMap<>();

        //Save Origin File
        String filename = MyFileUtils.makeRandFilename(extension);
        File originImage = MyFileUtils.saveFile(localDir.getPath() + "/" + filename, multipartFile);

        ImageInfo imageInfo = getImageInfo(originImage);
        long filesize = originImage.length();

        saves.put("origin",
                AttachImageSavedDto.response.builder()
                        .filename(filename)
                        .path(urlPath)
                        .filesize(filesize)
                        .imageInfo(imageInfo)
                        .build()
        );

        //TODO
        //Save Thumbnails
        Integer resizeWidth = 100;
        saves.put(("w" + resizeWidth), makeResizeImage(originImage, urlPath, localDir.getPath(), extension, 320));

        return saves;
    }

    private AttachImageSavedDto.response makeResizeImage(File originImage, String urlPath, String localDirPath, String extension, Integer resizeWidth) {
        if (extension.equalsIgnoreCase("gif")) {
            extension = "jpg";
        }

        String filename = MyFileUtils.makeRandFilename(extension);

        File resizeImage = new File(localDirPath, filename);
        resizeImage = this.resizeImage(originImage, resizeImage, resizeWidth);

        ImageInfo imageInfo = getImageInfo(resizeImage);
        long filesize = resizeImage.length();

        return AttachImageSavedDto.response.builder()
                .filename(filename)
                .path(urlPath)
                .filesize(filesize)
                .imageInfo(imageInfo)
                .build();

    }


    public boolean isImageFile(MultipartFile multipartFile) {
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());

            if (bi == null) {
                return false;
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public File resizeImage(File originFile, File resizeFile, Integer width) {
        try {
            Thumbnails.of(originFile).width(width).toFile(resizeFile);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }

        return resizeFile;

    }

    private ImageInfo getImageInfo(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return ImageInfo.builder()
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
    }
}
