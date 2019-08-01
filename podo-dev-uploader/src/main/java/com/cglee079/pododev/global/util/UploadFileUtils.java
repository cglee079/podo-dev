package com.cglee079.pododev.global.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UploadFileUtils {

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

    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static String makeRandFilename(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    public static String pathByDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = now.format(dateTimeFormatter);

        String[] values = date.split("/");

        return new StringBuilder()
                .append("/")
                .append(values[0])
                .append("/")
                .append(values[1])
                .append("/")
                .append(values[2])
                .toString();

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


    public static void forceMakeDir(String saveDirPath) {
        try {
            FileUtils.forceMkdir(new File(saveDirPath));
        } catch (IOException e) {
            //Todo
            e.printStackTrace();
        }
    }
}
