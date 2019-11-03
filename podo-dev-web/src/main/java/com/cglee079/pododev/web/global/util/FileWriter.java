package com.cglee079.pododev.web.global.util;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.attachimage.exception.InValidImageException;
import com.cglee079.pododev.web.global.exception.FailImageResizeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * 로컬에 이미지 저장
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FileWriter {

    @Value("${local.upload.base.dir}")
    private String baseDir;

    /**
     * URL로 부터 파일 저장
     *
     * @param path
     * @param urlStr
     * @return
     */
    public File saveFile(String path, String urlStr) {
        final String originName = FilenameUtils.getName(urlStr);
        final String originExtension = FilenameUtils.getExtension(originName);
        final String dirPath = PathUtil.merge(baseDir, path); // 로컬 저장경로
        final String filename = MyFileUtils.makeRandFilename(originExtension);

        log.info("File Writer, Save File By Url, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(PathUtil.merge(dirPath, filename), urlStr);
    }

    /**
     * MultipartFile 로 부터 파일 저장.
     *
     * @param path
     * @param multipartFile
     * @return
     */
    public File saveFile(String path, MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(originName);
        final String dirPath = PathUtil.merge(baseDir, path); // 로컬 저장경로


        //Save Origin File
        final String filename = MyFileUtils.makeRandFilename(extension);

        log.info("File Writer, Save File By Multipart, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(PathUtil.merge(dirPath, filename), multipartFile);
    }

    /**
     * Base64 로 부터 파일 저장
     *
     * @param path
     * @param extension
     * @return
     */
    public File saveFile(String path, String extension, String base64) {

        BufferedImage bufferedImage;

        try {
            final String base64Split = base64.split(",")[1];
            final byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Split);
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

        } catch (IOException e) {
            throw new InValidImageException();
        }

        final String dirPath = PathUtil.merge(baseDir, path); // 로컬 저장경로
        final String filename = MyFileUtils.makeRandFilename(extension);

        log.info("File Writer, Save File By Base64, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(PathUtil.merge(dirPath, filename), extension, bufferedImage);

    }

    /**
     * 이미지 리사이징
     *
     * @param originImage
     * @param path
     * @param resizeWidth
     * @return
     */
    public File resizeImage(File originImage, String path, Integer resizeWidth) {
        final String originName = originImage.getName();

        String extension = FilenameUtils.getExtension(originName);
        if (extension.equalsIgnoreCase("gif")
                || extension.equalsIgnoreCase("jfif")) {
            extension = "jpg";
        }

        File resizeFile = new File(PathUtil.merge(baseDir, path), MyFileUtils.makeRandFilename(extension));

        try {
            Thumbnails.of(originImage).width(resizeWidth).toFile(resizeFile);
        } catch (IOException e) {
            throw new FailImageResizeException();
        }

        return resizeFile;

    }

    public void removeFile(String path, File file) {

        String dirPath = PathUtil.merge(baseDir, path);
        String filename = file.getName();

        log.info("File Writer, Remove File, '{}', '{}'", dirPath, filename);

        MyFileUtils.deleteFile(dirPath, filename);
    }

    public void removeDirectory(String path) {
        String dirPath = PathUtil.merge(baseDir, path);

        log.info("File Writer, Remove Directory, '{}'", dirPath);

        MyFileUtils.deleteDirectory(dirPath);
    }
}
