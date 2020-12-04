package com.podo.pododev.web.global.util;

import com.podo.pododev.web.global.util.exception.FileProcessFailApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FileCRUDUtil {

    public static String readFile(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            log.error("파일 읽기 실패 : {}", file.getPath(), e);
            throw new FileProcessFailApiException(file.getPath());
        }
        return contentBuilder.toString();
    }

    public static File writeFile(String path, MultipartFile multipartFile) {

        try {
            final File file = new File(path);

            FileCRUDUtil.writeForceDirectory(file.getParentFile().getPath());

            multipartFile.transferTo(file);

            return file;

        } catch (IOException e) {
            log.error("파일 쓰기 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }
    }

    public static File writeFile(String path, String urlStr) {

        try {
            final File file = new File(path);

            FileCRUDUtil.writeForceDirectory(file.getParentFile().getPath());

            final FileOutputStream fos = new FileOutputStream(file);
            final URL url = new URL(urlStr);
            final URLConnection connection = url.openConnection();
            final InputStream in = connection.getInputStream();

            final byte[] buf = new byte[512];
            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    break;
                }
                fos.write(buf, 0, len);
            }

            in.close();
            fos.flush();
            fos.close();

            return file;

        } catch (IOException e) {
            log.error("파일 쓰기 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }
    }

    public static File writeFile(String path, String extension, BufferedImage bufferedImage) {
        File file = new File(path);
        try {
            FileCRUDUtil.writeForceDirectory(file.getParentFile().getPath());
            ImageIO.write(bufferedImage, extension, file);
            return file;
        } catch (IOException e) {
            log.error("파일 쓰기 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }

    }

    public static File writeForceDirectory(String path) {
        File dir = new File(path);
        try {
            FileUtils.forceMkdir(dir);
            return dir;
        } catch (IOException e) {
            log.error("폴더 생성 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }
    }

    public static void deleteFile(String path, String filename) {
        new File(path, filename).delete();
    }

    public static void deleteDirectory(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            log.error("폴더 삭제 실패 : {}", path, e);
            throw new FileProcessFailApiException(path);
        }
    }
}
