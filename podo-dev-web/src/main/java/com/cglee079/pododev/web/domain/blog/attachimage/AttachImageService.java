package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSavedDto;
import com.cglee079.pododev.core.global.util.MyFileUtils;
import com.cglee079.pododev.web.global.infra.uploader.PodoUploaderClient;
import com.cglee079.pododev.web.global.util.TempUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    @Value("${upload.base.dir}")
    private String baseDir;

    private final PodoUploaderClient podoUploaderClient;
    private final AttachImageRepository attachImageRepository;
    private final AttachImageWriter attachImageWriter;

    @Value("${upload.base.url}")
    private String baseUrl;

    /**
     * 이미지 업로드, 이미지를 우선 본서버에 저장.
     */
    public AttachImageDto.response saveImage(MultipartFile multipartFile) {
        String key = MyFileUtils.generateKey();
        String originName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originName);

        Map<String, AttachImageSavedDto.response> saves = attachImageWriter.makeSaveFile(multipartFile, extension);

        return AttachImageDto.response.builder()
                .key(key)
                .originName(originName)
                .domainUrl(TempUtil.getDomainUrl() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }

    public void uploadImage(List<AttachImageDto.insert> images) {
        //이미지 서버 저장
        images.forEach(image -> {
            List<AttachImageSavedDto.insert> saves = image.getSaves().values().stream().collect(Collectors.toList());

            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    saves.forEach(save ->
                            podoUploaderClient.uploadImages(save.getPath(), new File(baseDir + save.getPath(), save.getFilename()))
                    );
                    break;
                case BE:
                case REMOVE:
                case UNNEW:
                default:
                    break;
            }
        });

    }

    public void updateImage(Long blogSeq, List<AttachImageDto.update> images) {

        images.forEach(image -> {
            List<AttachImageSavedDto.update> saves = image.getSaves().values().stream().collect(Collectors.toList());

            //DB 정보 갱신
            switch (FileStatus.valueOf(image.getFileStatus())) {
                case NEW:
                    saves.forEach(save ->
                            podoUploaderClient.uploadImages(save.getPath(), new File(baseDir + save.getPath(), save.getFilename()))
                    );

                    attachImageRepository.save(image.toEntity(blogSeq));
                    break;
                case REMOVE:
                    saves.forEach(save ->
                            podoUploaderClient.deleteImage(save.getPath(), save.getFilename())
                    );

                    attachImageRepository.deleteById(image.getSeq());
                    break;
                case BE:
                case UNNEW:
                default:
                    break;
            }
        });


    }
}
