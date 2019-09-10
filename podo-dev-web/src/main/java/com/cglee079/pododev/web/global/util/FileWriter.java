package com.cglee079.pododev.web.global.util;

import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.domain.blog.attachimage.exception.InValidImageException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Component
public class FileWriter {

    @Value("${upload.base.dir}")
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
        try {
            base64 = base64.split(",")[1];
            final byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            final String dirPath = PathUtil.merge(baseDir, path); // 로컬 저장경로
            final String filename = MyFileUtils.makeRandFilename(extension);

            return MyFileUtils.saveFile(PathUtil.merge(dirPath, filename), extension, bufferedImage);
        } catch (IOException e) {
            throw new InValidImageException();
        }
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
            e.printStackTrace();
            //TODO
        }

        return resizeFile;

    }

    public void removeFile(String path, File file) {
        MyFileUtils.deleteFile(PathUtil.merge(baseDir, path), file.getName());
    }
}
