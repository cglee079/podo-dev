package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.FileStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.core.util.HttpUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    @Value("${local.upload.base.url}")
    private String baseUrl;

    private final static String PASTE_IMAGE_NAME = "image_by_paste";

    private final AttachImageWriter attachImageWriter;

    public AttachImageDto.response saveBase64(String base64) {
        String originExtension = "png";
        String originName = PASTE_IMAGE_NAME + "." + originExtension;

        final Map<String, AttachImageSave> saves = attachImageWriter.makeSaveBase64(base64, originExtension);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }

    public AttachImageDto.response saveImageUrl(String url) {
        final String originName = FilenameUtils.getName(url);

        final Map<String, AttachImageSave> saves = attachImageWriter.makeSaveUrl(url);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }


    /**
     * 이미지 업로드, 이미지를 로컬에 저장.
     */
    public AttachImageDto.response saveImage(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();

        log.info("Save Image '{}' >> ", originName);

        final Map<String, AttachImageSave> saves = attachImageWriter.makeSaves(multipartFile);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(HttpUrlUtil.getSeverDomain() + baseUrl)
                .fileStatus(FileStatus.NEW)
                .saves(saves)
                .build();
    }


}
