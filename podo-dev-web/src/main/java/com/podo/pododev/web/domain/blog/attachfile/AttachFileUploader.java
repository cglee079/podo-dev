package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.domain.blog.FileStatus;
import com.podo.pododev.web.global.infra.uploader.PodoUploaderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttachFileUploader {

    @Value("${local.upload.base.dir}")
    private String baseDir;

    private final PodoUploaderClient podoUploaderClient;

    /**
     * 게시글 수정 시, 첨부 파일 업데이트 to Uploader Server
     *
     * @param files
     */
    public void uploadFile(List<AttachFileDto.insert> files) {
        log.info("Update Files");

        files.forEach(file -> {
            log.info("File '{}', '{}'", file.getFileStatus(), file.getOriginName());

            switch (FileStatus.valueOf(file.getFileStatus())) {
                case NEW:
                    podoUploaderClient.upload(file.getPath(), new File(baseDir + file.getPath(), file.getFilename()));
                    break;
                case REMOVE:
                    podoUploaderClient.delete(file.getPath(), file.getFilename());
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });
    }

    public void deleteFile(String path, String filename) {
        podoUploaderClient.delete(path, filename);
    }
}
