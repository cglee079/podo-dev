package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.FileStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.infra.uploader.PodoUploaderClient;
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
public class AttachImageUploader {

    @Value("${local.upload.base.dir}")
    private String baseDir;

    private final PodoUploaderClient podoUploaderClient;

    /***
     * 게시글 작성 시, 이미지 업로드 To Uplaoder Server
     * @param images
     */
    public void uploadImage(List<AttachImageDto.insert> images) {
        for (AttachImageDto.insert image : images) {
            log.info("Image '{}', '{}'", image.getFileStatus(), image.getOriginName());

            final List<AttachImageSave> saves = new ArrayList<>(image.getSaves().values());

            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    for (AttachImageSave attachImageSave : saves) {
                        podoUploaderClient.upload(attachImageSave.getPath(), new File(PathUtil.merge(baseDir, attachImageSave.getPath()), attachImageSave.getFilename()));
                    }

                    break;
                case REMOVE:
                    for (AttachImageSave save : saves) {
                        podoUploaderClient.delete(save.getPath(), save.getFilename());
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
        podoUploaderClient.delete(path, filename);
    }
}
