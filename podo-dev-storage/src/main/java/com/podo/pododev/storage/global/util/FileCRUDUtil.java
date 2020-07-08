package com.podo.pododev.storage.global.util;

import com.podo.pododev.storage.global.util.exception.FileProcessFailApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileCRUDUtil {

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
}
