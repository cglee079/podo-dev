package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveDto;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.PathUtil;
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
        images.forEach(image -> {
            log.info("Image '{}', '{}'", image.getFileStatus(), image.getOriginName());

            final List<AttachImageSaveDto.insert> saves = new ArrayList<>(image.getSaves().values());

            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    saves.forEach(save ->
                            podoUploaderClient.upload(save.getPath(), new File(PathUtil.merge(baseDir, save.getPath()), save.getFilename()))
                    );

                    break;
                case REMOVE:
                    saves.forEach(save ->
                            podoUploaderClient.delete(save.getPath(), save.getFilename())
                    );

                    break;
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });


    }

    public void deleteImageFile(String path, String filename) {
        podoUploaderClient.delete(path, filename);
    }
}
