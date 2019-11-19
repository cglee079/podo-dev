package com.podo.pododev.web.domain.blog.attachimage;

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
    private String baseDir;

    private final PodoStorageClient podoStorageClient;

    /***
     * 게시글 작성 시, 이미지 업로드 To Uplaoder Server
     * @param attachImages
     */
    public void uploadFileOfAttachImages(List<AttachImageDto.insert> attachImages) {
        for (AttachImageDto.insert attachImage : attachImages) {
            log.info("Image '{}', '{}'", attachImage.getAttachStatus(), attachImage.getOriginName());

            final List<AttachImageSave> saves = new ArrayList<>(attachImage.getSaves().values());

            switch (attachImage.getAttachStatus()) {
                case NEW:
                    for (AttachImageSave attachImageSave : saves) {
                        podoStorageClient.upload(attachImageSave.getPath(), new File(PathUtil.merge(baseDir, attachImageSave.getPath()), attachImageSave.getFilename()));
                    }

                    break;
                case REMOVE:
                    for (AttachImageSave save : saves) {
                        podoStorageClient.delete(save.getPath(), save.getFilename());
                    }
                    break;
                case BE:
                case UNNEW:
                default:
                    break;
            }
        }


    }

    public void deleteImageFile(String path, String filename) {
        podoStorageClient.delete(path, filename);
    }
}
