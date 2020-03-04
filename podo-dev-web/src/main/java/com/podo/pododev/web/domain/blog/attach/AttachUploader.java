package com.podo.pododev.web.domain.blog.attach;

import com.podo.pododev.web.global.infra.storage.PodoStorageClient;
import com.podo.pododev.web.global.util.writer.LocalDirectoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttachUploader {

    private final LocalDirectoryManager localDirectoryManager;
    private final PodoStorageClient podoStorageClient;

    public void uploadToStorage(List<AttachVo> attachVos){
        for (AttachVo attach : attachVos) {
            final String filePath = attach.getFilePath();
            final File savedFileOfAttachImageSave = new File(localDirectoryManager.mergeLocalSaveBasedDirectory(filePath), attach.getFilename());
            podoStorageClient.uploadFile(filePath, savedFileOfAttachImageSave);
        }
    }

    public void deleteToStorage(List<AttachVo> attachVos) {
        for (AttachVo attachVo : attachVos) {
            podoStorageClient.deleteFile(attachVo.getFilePath(), attachVo.getFilename());
        }
    }

}