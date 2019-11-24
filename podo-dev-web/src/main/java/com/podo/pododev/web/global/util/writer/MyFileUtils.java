package com.podo.pododev.web.global.util.writer;

import com.podo.pododev.core.rest.exception.FileProcessFailException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MyFileUtils {

    public static String createPathByDate(LocalDateTime time) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd");

        return time.format(formatter);
    }

    public static String createRandFilename(String extension) {
        String filename = UUID.randomUUID() + "";
        if (!StringUtils.isEmpty(extension)) {
            filename += "." + extension;
        }
        return filename;
    }

    public static File writeFile(String path, MultipartFile multipartFile) {

        try {
            final File file = new File(path);

            MyFileUtils.writeForceDirectory(file.getParentFile().getPath());

            multipartFile.transferTo(file);

            return file;

        } catch (IOException e) {
            throw new FileProcessFailException(e);
        }
    }

    public static File writeFile(String path, String urlStr) {

        try {
            final File file = new File(path);

            MyFileUtils.writeForceDirectory(file.getParentFile().getPath());

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
            throw new FileProcessFailException(e);
        }
    }

    public static File writeFile(String path, String extension, BufferedImage bufferedImage) {
        File file = new File(path);
        try {
            MyFileUtils.writeForceDirectory(file.getParentFile().getPath());
            ImageIO.write(bufferedImage, extension, file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessFailException(e);
        }

    }

    public static File writeForceDirectory(String path) {
        File dir = new File(path);
        try {
            FileUtils.forceMkdir(dir);
            return dir;
        } catch (IOException e) {
            throw new FileProcessFailException(e);
        }
    }

    public static void deleteFile(String path, String filename) {
        new File(path, filename).delete();
    }

    public static void deleteDirectory(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            throw new FileProcessFailException(e);
        }
    }
}
