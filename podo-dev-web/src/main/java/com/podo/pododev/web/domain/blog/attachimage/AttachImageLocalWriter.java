package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.domain.blog.attachimage.exception.InvalidImageException;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.util.writer.FileLocalWriter;
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
public class AttachImageLocalWriter {


    @Value("${local.upload.sub.image.dir}")
    private String imageSaveDirectory;

    private static final String ORIGIN_IMAGE_KEY = "origin";

    private final FileLocalWriter fileLocalWriter;

    public Map<String, AttachImageSave> makeSaveUrl(String url) {
        if (!AttachImageValidator.isImageFile(url)) {
            throw new InvalidImageException();
        }

        final String path = PathUtil.merge(imageSaveDirectory, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.write(originPath, url);

        saves.put(ORIGIN_IMAGE_KEY, makeOrigin(originPath, originImage));

        return saves;
    }

    public Map<String, AttachImageSave> makeSaveBase64(String base64, String extension) {
        final String path = PathUtil.merge(imageSaveDirectory, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        //Save Origin File
        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.write(originPath, base64, extension);
        saves.put(ORIGIN_IMAGE_KEY, makeOrigin(originPath, originImage));

        return saves;
    }

    public Map<String, AttachImageSave> makeSaves(MultipartFile multipartFile) {
        if (!AttachImageValidator.isImageFile(multipartFile)) {
            throw new InvalidImageException();
        }

        log.info("Save Image Each Size, '{}'", multipartFile.getOriginalFilename());

        final String path = PathUtil.merge(imageSaveDirectory, MyFileUtils.makeDatePath(LocalDateTime.now())); // 로컬 저장경로
        final Map<String, AttachImageSave> saves = new HashMap<>();

        //Save Origin File
        final String originPath = PathUtil.merge(path, ORIGIN_IMAGE_KEY);
        final File originImage = fileLocalWriter.write(originPath, multipartFile);

        saves.put(ORIGIN_IMAGE_KEY, makeOrigin(originPath, originImage));


        return saves;
    }


    private AttachImageSave makeOrigin(String path, File originImage) {
        final ImageSizeVo imageSizeVo = getImageInfo(originImage);

        return AttachImageSave.builder()
                .imageKey(ORIGIN_IMAGE_KEY)
                .filename(originImage.getName())
                .path(path)
                .filesize(originImage.length())
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

    /**
     * //TODO
     * 이미지 정보를 가져옴
     *
     * @param file
     * @return
     */
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
