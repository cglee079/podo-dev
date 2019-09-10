package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.attachimage.ImageInfo;
import com.cglee079.pododev.web.domain.blog.attachimage.exception.InValidImageException;
import com.cglee079.pododev.web.global.util.FileWriter;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageSaveService {

    @Value("${upload.postfix.image.dir}")
    private String imageDir;

    private final FileWriter fileWriter;

    public Map<String, AttachImageSaveDto.response> makeSaveUrl(String url) {
        if (!this.isImageFile(url)) {
            throw new InValidImageException();
        }

        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath()); // 로컬 저장경로
        final Map<String, AttachImageSaveDto.response> saves = new HashMap<>();

        final File originImage = fileWriter.saveFile(path, url);
        final ImageInfo imageInfo = getImageInfo(originImage);

        saves.put("origin",
                AttachImageSaveDto.response.builder()
                        .filename(originImage.getName())
                        .path(path)
                        .filesize(originImage.length())
                        .imageInfo(imageInfo)
                        .build()
        );

        return saves;
    }

    public Map<String, AttachImageSaveDto.response> makeSaveBase64(String base64, String extension) {
        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath()); // 로컬 저장경로
        final Map<String, AttachImageSaveDto.response> saves = new HashMap<>();

        //Save Origin File
        final File originImage = fileWriter.saveFile(path, extension, base64);
        final ImageInfo imageInfo = getImageInfo(originImage);

        saves.put("origin",
                AttachImageSaveDto.response.builder()
                        .filename(originImage.getName())
                        .path(path)
                        .filesize(originImage.length())
                        .imageInfo(imageInfo)
                        .build()
        );

        return saves;
    }

    public Map<String, AttachImageSaveDto.response> makeSaves(MultipartFile multipartFile) {
        if (!this.isImageFile(multipartFile)) {
            //TODO throw exception..
            return null;
        }

        log.info("Save Image Each Size, '{}'", multipartFile.getOriginalFilename());

        final String path = PathUtil.merge(imageDir, MyFileUtils.makeDatePath()); // 로컬 저장경로
        final Map<String, AttachImageSaveDto.response> saves = new HashMap<>();

        //Save Origin File
        final File originImage = fileWriter.saveFile(path, multipartFile);
        final ImageInfo imageInfo = getImageInfo(originImage);

        saves.put("origin",
                AttachImageSaveDto.response.builder()
                        .filename(originImage.getName())
                        .path(path)
                        .filesize(originImage.length())
                        .imageInfo(imageInfo)
                        .build()
        );


//        //Resize Image
//        Integer resizeWidth = 100;
//        saves.put(("w" + resizeWidth), saveResizeImage(originImage, path, resizeWidth));

        return saves;
    }

    private AttachImageSaveDto.response saveResizeImage(File originImage, String path, Integer resizeWidth) {
        log.info("Resize Image, size : '{}', from : '{}'", resizeWidth, originImage.getName());

        final File resizeImage = fileWriter.resizeImage(originImage, path, resizeWidth);
        final ImageInfo imageInfo = getImageInfo(resizeImage);

        return AttachImageSaveDto.response.builder()
                .filename(resizeImage.getName())
                .path(path)
                .filesize(resizeImage.length())
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
     * @param url
     * @return 유효여부
     */
    public boolean isImageFile(String url) {
        if (Objects.isNull(url)) {
            return false;
        }

        try {

            BufferedImage image = ImageIO.read(new URL(url));

            if (Objects.isNull(image)) {
                return false;
            }

            return true;

        } catch (MalformedURLException e) {
            return false;

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
