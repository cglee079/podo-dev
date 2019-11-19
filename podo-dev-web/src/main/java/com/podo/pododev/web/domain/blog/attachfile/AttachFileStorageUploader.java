package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.global.infra.storage.PodoStorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttachFileStorageUploader {

    @Value("${local.upload.base.dir}")
    private String baseDir;

    private final PodoStorageClient podoStorageClient;

    /**
     * 게시글 수정 시, 첨부 파일 업데이트 to Uploader Server
     *
     * @param files
     */
    public void uploadFileOfAttachFiles(List<AttachFileDto.insert> files) {
        log.info("Update Files");

        files.forEach(file -> {
            log.info("File '{}', '{}'", file.getAttachStatus(), file.getOriginName());

            switch (file.getAttachStatus()) {
                case NEW:
                    podoStorageClient.upload(file.getPath(), new File(baseDir + file.getPath(), file.getFilename()));
                    break;
                case REMOVE:
                    podoStorageClient.delete(file.getPath(), file.getFilename());
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });
    }

    public void deleteFile(String path, String filename) {
        podoStorageClient.delete(path, filename);
    }
}
