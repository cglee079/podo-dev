package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.attachimage.exception.InvalidImageException;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
import com.podo.pododev.core.util.MyPathUtils;
import com.podo.pododev.web.global.util.writer.MyFileUtils;
import com.podo.pododev.web.global.util.writer.MyFilenameUtils;
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
public class AttachImageLocalWriter {

    @Value("${local.upload.sub.image.dir}")
    private String localAttachImageDirectory;

    private static final String ORIGIN_IMAGE_KEY = "origin";

    private final FileLocalWriter fileLocalWriter;

    public Map<String, AttachImageSave> writeImageToMultipleSizeFromImageUrl(String imageUrl) {
        if (!ImageValidator.isImageFile(imageUrl)) {
            throw new InvalidImageException();
        }

        final String localSavedLocation = MyPathUtils.merge(localAttachImageDirectory, MyFilenameUtils.createPathByDate(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> attachImageSaveMap = new HashMap<>();

        final String localSavedLocationOfOrigin = MyPathUtils.merge(localSavedLocation, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.writeFromUrl(imageUrl, localSavedLocationOfOrigin);

        attachImageSaveMap.put(ORIGIN_IMAGE_KEY, createAttachImageSave(originImage, localSavedLocationOfOrigin, ORIGIN_IMAGE_KEY));

        return attachImageSaveMap;
    }

    public Map<String, AttachImageSave> writeImageToMultipleSizeFromBase64(String base64, String extension) {
        final String localSavedLocation = MyPathUtils.merge(localAttachImageDirectory, MyFilenameUtils.createPathByDate(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> attachImageSaveMap = new HashMap<>();

        final String localSavedLocationOfOrigin = MyPathUtils.merge(localSavedLocation, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.writeByBase64(base64, extension, localSavedLocationOfOrigin);
        attachImageSaveMap.put(ORIGIN_IMAGE_KEY, createAttachImageSave(originImage, localSavedLocationOfOrigin, ORIGIN_IMAGE_KEY));

        return attachImageSaveMap;
    }

    public Map<String, AttachImageSave> writeImageToMultipleSizeFromMultipartFile(MultipartFile multipartFile) {
        if (!ImageValidator.isImageFile(multipartFile)) {
            throw new InvalidImageException();
        }

        final String localSavedLocation = MyPathUtils.merge(localAttachImageDirectory, MyFilenameUtils.createPathByDate(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> attachImageSaveMap = new HashMap<>();

        final String localSavedLocationOfOrigin = MyPathUtils.merge(localSavedLocation, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.writeFromMultipartFile(multipartFile, localSavedLocationOfOrigin);

        attachImageSaveMap.put(ORIGIN_IMAGE_KEY, createAttachImageSave(originImage, localSavedLocationOfOrigin, ORIGIN_IMAGE_KEY));

        return attachImageSaveMap;
    }


    private AttachImageSave createAttachImageSave(File imageFile, String filePath, String imageKey) {
        final ImageSizeVo imageSizeVo = getImageInfo(imageFile);

        return AttachImageSave.builder()
                .imageKey(imageKey)
                .filename(imageFile.getName())
                .filePath(filePath)
                .filesize(imageFile.length())
                .height(imageSizeVo.getHeight())
                .width(imageSizeVo.getWidth())
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

    private ImageSizeVo getImageInfo(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return new ImageSizeVo(image.getWidth(), (image.getHeight()));

        } catch (IOException e) {
            log.error("이미지 파일 정보를 가져 올 수 없습니다");
            throw new InvalidImageException();
        }
    }
}
