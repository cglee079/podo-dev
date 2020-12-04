package com.podo.pododev.web.domain.blog.attach.attachimage.application;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import com.podo.pododev.web.domain.blog.attach.attachimage.AttachImageLocalWriter;
import com.podo.pododev.web.domain.blog.attach.attachimage.dto.AttachImageResponse;
import com.podo.pododev.web.domain.blog.attach.attachimage.model.AttachImageSave;
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
public class AttachImageWriteFileService {

    private final static String BASE64_IMAGE_NAME = "base64";

    private final AttachLinkManager attachLinkManager;
    private final AttachImageLocalWriter attachImageLocalWriter;

    public AttachImageResponse saveByBase64(String base64) {
        final String originFileExtension = "png";
        final String originFilename = BASE64_IMAGE_NAME + "." + originFileExtension;

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.writeImageToMultipleSizeFromBase64(base64, originFileExtension);

        return AttachImageResponse.builder()
                .originFilename(originFilename)
                .uploadedUrl(attachLinkManager.getLocalSavedUrl())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }

    public AttachImageResponse saveByImageUrl(String imageUrl) {
        final String originFilename = FilenameUtils.getName(imageUrl);

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.writeImageToMultipleSizeFromImageUrl(imageUrl);

        return AttachImageResponse.builder()
                .originFilename(originFilename)
                .uploadedUrl(attachLinkManager.getLocalSavedUrl())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }


    public AttachImageResponse saveByMultipartFile(MultipartFile multipartFile) {
        final String originName = multipartFile.getOriginalFilename();

        final Map<String, AttachImageSave> saves = attachImageLocalWriter.writeImageToMultipleSizeFromMultipartFile(multipartFile);

        return AttachImageResponse.builder()
                .originFilename(originName)
                .uploadedUrl(attachLinkManager.getLocalSavedUrl())
                .attachStatus(AttachStatus.NEW)
                .saves(saves)
                .build();
    }


}
