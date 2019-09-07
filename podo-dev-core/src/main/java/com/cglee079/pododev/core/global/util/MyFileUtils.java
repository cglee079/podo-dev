package com.cglee079.pododev.core.global.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public static String generateKey() {
        return UUID.randomUUID().toString();
    }

    public static String makeDatePath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd");
        String path = LocalDateTime.now().format(formatter);

        return path;
    }

    public static String makeRandFilename(String extension) {
        String filename = UUID.randomUUID() + "";
        if (!StringUtils.isEmpty(extension)) {
            filename += "." + extension;
        }
        return filename;
    }

    public static File saveFile(String path, MultipartFile multipartFile) {

        try {
            final File file = new File(path);

            MyFileUtils.makeForceDir(file.getParentFile().getPath());

            multipartFile.transferTo(file);

            return file;

        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
    }

    public static File saveFile(String path, String urlStr) {

        try {
            final File file = new File(path);

            MyFileUtils.makeForceDir(file.getParentFile().getPath());

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
            e.printStackTrace();
            //TODO
            throw new RuntimeException();
        }
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
