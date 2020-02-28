package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.core.util.PathUtil;
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
    private String localSavedBaseDirectory;

    private final PodoStorageClient podoStorageClient;

    public void writeFileOfAttachFilesToStorage(List<AttachFileDto.insert> fileInserts) {
        for (AttachFileDto.insert fileInsert : fileInserts) {
            writeAttachFileToStorage(fileInsert);
        }
    }

    private void writeAttachFileToStorage(AttachFileDto.insert fileInsert) {
        log.info("'{}' 첨부파일을 서버에 저장합니다, 파일상태 : '{}'", fileInsert.getOriginFilename(), fileInsert.getAttachStatus());

        switch (fileInsert.getAttachStatus()) {
            case NEW:
                final File localSavedAttachFile = new File(PathUtil.merge(localSavedBaseDirectory, fileInsert.getFilePath()), fileInsert.getFilename());
                podoStorageClient.uploadFile(fileInsert.getFilePath(), localSavedAttachFile);
                break;
            case REMOVE:
                podoStorageClient.deleteFile(fileInsert.getFilePath(), fileInsert.getFilename());
            case BE:
            case UNNEW:
            default:
                break;
        }
    }

    public void deleteFileOfAttachFile(String path, String filename) {
        podoStorageClient.deleteFile(path, filename);
    }
}
