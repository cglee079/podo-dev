package com.podo.pododev.storage.upload;

import com.podo.pododev.core.rest.exception.FileProcessFailException;
import com.podo.pododev.core.util.MyPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MyFileUtils {


    public static File writeFile(String path, MultipartFile multipartFile) {
        log.info("'{}' 파일을 저장합니다.", multipartFile.getOriginalFilename());
        try {
            final File file = new File(path);

            multipartFile.transferTo(file);

            return file;

        } catch (IOException e) {
            log.error("'{}' 파일을 저장에러 : '{}'", multipartFile.getOriginalFilename(), e);
            throw new FileProcessFailException(e);
        }
    }

    public static File writeForceDirectory(String path) {
        log.info("'{}' 폴더를 생성합니다.", path);
        File dir = new File(path);
        try {
            FileUtils.forceMkdir(dir);
            return dir;
        } catch (IOException e) {
            log.error("'{}' 폴더를 생성에러 : {}", path, e);
            throw new FileProcessFailException(e);
        }
    }

    public static void deleteFile(String path, String filename) {
        log.info("'{}' 파일을 생성합니다.", MyPathUtils.merge(path, filename));

        new File(path, filename).delete();
    }

}
