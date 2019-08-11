package com.cglee079.pododev.web.global.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MyFileUtils {

    public static String generateKey() {
        return UUID.randomUUID().toString();
    }

    public static String dayDirPath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd");
        String path = LocalDateTime.now().format(formatter);

        return path;
    }

    public static File saveFile(String path, MultipartFile multipartFile) {
        try {
            File file = new File(path);
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
    }

    public static String makeRandFilename(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    public static boolean isImageFile(MultipartFile multipartFile) {
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

    public static File resizeImage(File originFile, File resizeFile, Integer width) {
        try {
            Thumbnails.of(originFile).width(width).toFile(resizeFile);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }

        return resizeFile;

    }

    public static File makeForceDir(String path) {
        File dir = new File(path);
        try {
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }

        return dir;
    }

    public static void deleteFile(String path, String filename) {
        new File(path, filename).delete();
    }
}
