package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSavedDto;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.MyFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AttachImageUploader {

    @Value("${upload.base.dir}")
    private String baseDir;

    @Value("${upload.postfix.image.dir}")
    private String imageDir;

    @Value("${upload.base.url}")
    private String baseUrl;


    private final PodoUploaderClient podoUploaderClient;

    /**
     * 게시글 작성, 이미지 서버 업로드.
     *
     * @param images
     */
    public void handleUploadImage(List<AttachImageDto.insert> images) {

        images.forEach(image -> {
            List<AttachImageSavedDto.insert> saves = image.getSaves().values().stream().collect(Collectors.toList());


            //이미지 상태값에 따라서 분기
            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW: // 서버에 파일 업로드, DB 업로드
                    saves.forEach(save ->
                            podoUploaderClient.uploadImages(save.getPath(), new File(baseDir + save.getPath(), save.getFilename()))
                    );

                    //  attachImageRepository.save(image.toEntity(blogSeq));

                case REMOVE: // 서버에서 파일 삭제
                    saves.forEach(save -> {
                        // podoUploaderClient.deleteImage(image.getBasePath(), save.getFilename());
                    });

                    //     attachImageRepository.delete(image.toEntity(blogSeq));
                    break;

                case UNNEW: // 로컬에서 파일 삭제
                    saves.forEach(save ->
                            MyFileUtils.deleteFile(baseDir + save.getPath(), save.getFilename())
                    );

                    break;
                case BE:

                    break;
            }
        });

    }
}
