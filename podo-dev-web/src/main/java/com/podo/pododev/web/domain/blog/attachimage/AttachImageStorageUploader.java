package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.infra.storage.PodoStorageClient;
import com.podo.pododev.core.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttachImageStorageUploader {

    @Value("${local.upload.base.dir}")
    private String localSavedBaseDirectory;

    private final PodoStorageClient podoStorageClient;

    public void writeFileOfAttachImagesToStorage(List<AttachImageDto.insert> attachImages) {
        for (AttachImageDto.insert attachImage : attachImages) {
            log.info("Image '{}', '{}'", attachImage.getAttachStatus(), attachImage.getOriginFilename());

            final ArrayList<AttachImageSave> attachImageSaves = new ArrayList<>(attachImage.getSaves().values());
            writeFileOfAttachImageSavesToStorage(attachImageSaves, attachImage.getAttachStatus());
        }
    }

    private void writeFileOfAttachImageSavesToStorage(List<AttachImageSave> attachImageSaves, AttachStatus attachStatus) {

        switch (attachStatus) {
            case NEW:
                for (AttachImageSave attachImageSave : attachImageSaves) {
                    final String filePath = attachImageSave.getFilePath();
                    final File savedFileOfAttachImageSave = new File(PathUtil.merge(localSavedBaseDirectory, filePath), attachImageSave.getFilename());
                    podoStorageClient.uploadFile(filePath, savedFileOfAttachImageSave);
                }

                break;
            case REMOVE:
                for (AttachImageSave attachImageSave : attachImageSaves) {
                    podoStorageClient.deleteFile(attachImageSave.getFilePath(), attachImageSave.getFilename());
                }
                break;
            case BE:
            case UNNEW:
            default:
                break;
        }
    }

    public void deleteFileOfAttachImage(String path, String filename) {
        podoStorageClient.deleteFile(path, filename);
    }
}
