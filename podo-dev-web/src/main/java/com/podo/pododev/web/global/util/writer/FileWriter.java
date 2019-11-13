package com.podo.pododev.web.global.util.writer;

import com.podo.pododev.core.util.MyFileUtils;
import com.podo.pododev.web.domain.blog.attachimage.exception.InvalidImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
public class FileWriter extends AbstractWriter {

    /**
     * URL로 부터 파일 저장
     *
     * @param filepath
     * @param urlStr
     * @return
     */
    public File write(String filepath, String urlStr) {
        final String originName = FilenameUtils.getName(urlStr);
        final String originExtension = FilenameUtils.getExtension(originName);
        final String dirPath = mergeLocalBaseLocation(filepath); // 로컬 저장경로
        final String filename = MyFileUtils.makeRandFilename(originExtension);

        log.info("File Writer, Save File By Url, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(mergeLocalBaseLocation(filename), urlStr);
    }

    /**
     * MultipartFile 로 부터 파일 저장.
     *
     * @param path
     * @param multipartFile
     * @return
     */
    public File write(String path, MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(originName);
        final String dirPath = mergeLocalBaseLocation(path); // 로컬 저장경로


        //Save Origin File
        final String filename = MyFileUtils.makeRandFilename(extension);

        log.info("File Writer, Save File By Multipart, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(mergeLocalBaseLocation(filename), multipartFile);
    }

    /**
     * Base64 로 부터 파일 저장
     *
     * @param path
     * @param extension
     * @return
     */
    public File write(String path, String base64, String extension) {

        BufferedImage bufferedImage;

        try {
            final String base64Split = base64.split(",")[1];
            final byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Split);
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

        } catch (IOException e) {
            throw new InvalidImageException();
        }

        final String dirPath = mergeLocalBaseLocation(path); // 로컬 저장경로
        final String filename = MyFileUtils.makeRandFilename(extension);

        log.info("File Writer, Save File By Base64, '{}', '{}'", dirPath, filename);

        return MyFileUtils.saveFile(mergeLocalBaseLocation(filename), extension, bufferedImage);

    }

    public void removeFile(String path, File file) {

        String dirPath = mergeLocalBaseLocation(path);
        String filename = file.getName();

        log.info("File Writer, Remove File, '{}', '{}'", dirPath, filename);

        MyFileUtils.deleteFile(dirPath, filename);
    }

    public void removeDirectory(String path) {
        String dirPath = mergeLocalBaseLocation(path);

        log.info("File Writer, Remove Directory, '{}'", dirPath);

        MyFileUtils.deleteDirectory(dirPath);
    }
}
