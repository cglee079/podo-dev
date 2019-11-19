package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AttachImageService {

    private final static String PASTE_IMAGE_NAME = "paste";

    private final AttachLinkManager attachLinkManager;
    private final AttachImageLocalWriter attachImageLocalWriter;

    public AttachImageDto.response saveByBase64(String base64) {
        String originExtension = "png";
        String originName = PASTE_IMAGE_NAME + "." + originExtension;

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.makeSaveBase64(base64, originExtension);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(attachLinkManager.getLocalSavedLink())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }

    public AttachImageDto.response saveByImageUrl(String url) {
        final String originName = FilenameUtils.getName(url);

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.makeSaveUrl(url);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(attachLinkManager.getLocalSavedLink())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }


    /**
     * 이미지 업로드, 이미지를 로컬에 저장.
     */
    public AttachImageDto.response saveByFileOfImage(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();

        log.info("Save Image '{}' >> ", originName);

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.makeSaves(multipartFile);

        return AttachImageDto.response.builder()
                .originName(originName)
                .uploadedUrl(attachLinkManager.getLocalSavedLink())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }


}
