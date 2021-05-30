package com.podo.pododev.backend.domain.blog.attach.attachimage.dto;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSave;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSaveEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class AttachImageResponse {

    private Long id;
    private String originFilename;
    private String uploadedUrl;
    private AttachStatus attachStatus;
    private Map<String, AttachImageSave> saves;

    @Builder
    public AttachImageResponse(Long id, String originFilename, String uploadedUrl, AttachStatus attachStatus, Map<String, AttachImageSave> saves) {
        this.id = id;
        this.originFilename = originFilename;
        this.uploadedUrl = uploadedUrl;
        this.attachStatus = attachStatus;
        this.saves = saves;
    }

    public static AttachImageResponse createByAttachImage(AttachImage image, String uploadedUrl, AttachStatus attachStatus) {
        final Map<String, AttachImageSave> saves = image.getSaves().stream()
                .map(AttachImageSaveEntity::getAttachImageSave)
                .collect(Collectors.toMap(AttachImageSave::getImageKey, x -> x));

        return AttachImageResponse.builder()
                .id(image.getId())
                .originFilename(image.getOriginFilename())
                .uploadedUrl(uploadedUrl)
                .attachStatus(attachStatus)
                .saves(saves)
                .build();


    }

}
