package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.attachimage.ImageInfo;
import com.cglee079.pododev.web.global.util.FileWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageSaveService {
    @Value("${upload.postfix.image.dir}")
    private String imageDir;

    private final FileWriter fileWriter;

    public Map<String, AttachImageSaveDto.response> makeSaveFile(MultipartFile multipartFile) {
        if (!this.isImageFile(multipartFile)) {
            //TODO throw exception..
            return null;
        }

        return saveFile(multipartFile);
    }


    private Map<String, AttachImageSaveDto.response> saveFile(MultipartFile multipartFile) {
        log.info("Save Image Each Size, '{}'", multipartFile.getOriginalFilename());

        String path = MyFileUtils.makeDatePath() + imageDir; // 로컬 저장경로

        Map<String, AttachImageSaveDto.response> saves = new HashMap<>();

        //Save Origin File
        File originImage = fileWriter.saveFile(path, multipartFile);

        ImageInfo imageInfo = getImageInfo(originImage);

        saves.put("origin",
                AttachImageSaveDto.response.builder()
                        .filename(originImage.getName())
                        .path(path)
                        .filesize(originImage.length())
                        .imageInfo(imageInfo)
                        .build()
        );

        //TODO
        //Save Thumbnails
        Integer resizeWidth = 100;
        saves.put(("w" + resizeWidth), saveResizeImage(originImage, path, resizeWidth));

        return saves;
    }

    private AttachImageSaveDto.response saveResizeImage(File originImage, String path, Integer resizeWidth) {
        log.info("Resize Image, size : '{}', from : '{}'", resizeWidth, originImage.getName());

        File resizeImage = fileWriter.resizeImage(originImage, path, resizeWidth);

        ImageInfo imageInfo = getImageInfo(resizeImage);
        long filesize = resizeImage.length();

        return AttachImageSaveDto.response.builder()
                .filename(resizeImage.getName())
                .path(path)
                .filesize(filesize)
                .imageInfo(imageInfo)
                .build();

    }

    /**
     * 이미지 파일 검증
     *
     * @param multipartFile
     * @return
     */
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

    /**
     * 이미지 정보를 가져옴
     *
     * @param file
     * @return
     */
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
