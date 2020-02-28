package com.podo.pododev.web.global.writer;

import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.web.domain.blog.attachimage.exception.InvalidImageBase64ApiException;
import com.podo.pododev.web.global.util.MyFileUtil;
import com.podo.pododev.web.global.util.MyFilenameUtil;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class FileLocalWriter {

    private final LocalDirectoryManager localDirectoryManager;

    public File writeFromUrl(String fileUrl, String savedDirectory) {
        final String originFilenameByUrl = FilenameUtils.getName(fileUrl);
        final String originFileExtension = FilenameUtils.getExtension(originFilenameByUrl);
        final String randomFilename = MyFilenameUtil.createRandFilename(originFileExtension);
        final String savedFilePath = PathUtil.merge(savedDirectory, randomFilename);

        log.info("'{}' 파일을 URL로 부터 저장합니다. URL : '{}', ", savedFilePath, fileUrl);

        return MyFileUtil.writeFile(localDirectoryManager.mergeLocalSaveBasedDirectory(savedFilePath), fileUrl);
    }

    public File writeFromMultipartFile(MultipartFile multipartFile, String savedDirectory) {
        final String originFilename = multipartFile.getOriginalFilename();
        final String originFileExtension = FilenameUtils.getExtension(originFilename);
        final String randomFilename = MyFilenameUtil.createRandFilename(originFileExtension);
        final String savedFilePath = PathUtil.merge(savedDirectory, randomFilename);

        log.info("'{}' 파일을 MultipartFile로부터 저장합니다", savedFilePath);

        return MyFileUtil.writeFile(localDirectoryManager.mergeLocalSaveBasedDirectory(savedFilePath), multipartFile);
    }

    public File writeByBase64(String base64, String extension, String savedDirectory) {
        final BufferedImage bufferedImageFromBase64 = getBufferedImageFromBase64(base64);
        final String randomFilename = MyFilenameUtil.createRandFilename(extension);
        final String savedFilePath = PathUtil.merge(savedDirectory, randomFilename);

        log.info("'{}' 파일을 BASE64로부터 저장합니다", randomFilename);

        return MyFileUtil.writeFile(localDirectoryManager.mergeLocalSaveBasedDirectory(savedFilePath), extension, bufferedImageFromBase64);
    }

    private BufferedImage getBufferedImageFromBase64(String base64) {
        try {
            final String base64Split = base64.split(",")[1];
            final byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Split);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            throw new InvalidImageBase64ApiException(base64);
        }
    }

    public void removeFile(String directory, String filename) {

        final String dirPath = localDirectoryManager.mergeLocalSaveBasedDirectory(directory);

        log.info("'{}' 파일을 삭제합니다.", filename);

        MyFileUtil.deleteFile(dirPath, filename);
    }

    public void removeDirectory(String directory) {
        final String directoryPath = localDirectoryManager.mergeLocalSaveBasedDirectory(directory);

        log.info("'{}' 폴더를 삭제합니다", directory);

        MyFileUtil.deleteDirectory(directoryPath);
    }
}
