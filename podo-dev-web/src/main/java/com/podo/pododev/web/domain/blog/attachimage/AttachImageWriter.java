package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.domain.blog.attachimage.exception.InvalidImageException;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.util.writer.FileWriter;
import com.podo.pododev.core.util.PathUtil;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageWriter {

    public static final String ORIGIN_IMAGE_ID = "origin";

    @Value("${local.upload.sub.image.dir}")
    private String imageDir;

    private final FileWriter fileWriter;

    public Map<String, AttachImageSave> makeSaveUrl(String url) {
        if (!AttachImageValidator.isImageFile(url)) {
            throw new InvalidImageException();
        }

        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_ID);
        final File originImage = fileWriter.write(originPath, url);

        saves.put(ORIGIN_IMAGE_ID, makeOrigin(originPath, originImage));

        return saves;
    }

    public Map<String, AttachImageSave> makeSaveBase64(String base64, String extension) {
        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        //Save Origin File
        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_ID);
        final File originImage = fileWriter.write(originPath, base64, extension);
        saves.put(ORIGIN_IMAGE_ID, makeOrigin(originPath, originImage));

        return saves;
    }

    public Map<String, AttachImageSave> makeSaves(MultipartFile multipartFile) {
        if (!AttachImageValidator.isImageFile(multipartFile)) {
            throw new InvalidImageException();
        }

        log.info("Save Image Each Size, '{}'", multipartFile.getOriginalFilename());

        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        //Save Origin File
        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_ID);
        final File originImage = fileWriter.write(originPath, multipartFile);

        saves.put(ORIGIN_IMAGE_ID, makeOrigin(originPath, originImage));


        return saves;
    }


    private AttachImageSave makeOrigin(String path, File originImage) {
        final ImageInfoVo imageInfoVo = getImageInfo(originImage);

        return AttachImageSave.builder()
                .imageId(ORIGIN_IMAGE_ID)
                .filename(originImage.getName())
                .path(path)
                .filesize(originImage.length())
                .height(imageInfoVo.getHeight())
                .width(imageInfoVo.getWidth())
                .build();
    }

//    private AttachImageSave saveResizeImage(File originImage, String path, Integer resizeWidth) {
//        log.info("Resize Image, size : '{}', from : '{}'", resizeWidth, originImage.getName());
//
//        final File resizeImage = fileWriter.resizeImage(originImage, path, resizeWidth);
//        final ImageInfo imageInfo = getImageInfo(resizeImage);
//
//        return AttachImageSave.builder()
//                .filename(resizeImage.getName())
//                .path(path)
//                .filesize(resizeImage.length())
//                .height(imageInfo.getHeight())
//                .width(imageInfo.getWidth())
//                .build();
//
//    }

    /**
     * 이미지 정보를 가져옴
     *
     * @param file
     * @return
     */
    private ImageInfoVo getImageInfo(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return ImageInfoVo.builder()
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .build();

        } catch (IOException e) {
            log.error("이미지 파일 정보를 가져 올 수 없습니다");
            throw new InvalidImageException();
        }
    }
}
