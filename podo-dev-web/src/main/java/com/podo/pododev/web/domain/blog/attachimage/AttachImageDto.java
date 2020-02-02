package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class AttachImageDto {

    @Getter
    public static class upload {
        private String imageUrl;
        private String base64;
    }

    @Getter
    public static class response {
        private Long id;
        private String originFilename;
        private String uploadedUrl;
        private AttachStatus attachStatus;
        private Map<String, AttachImageSave> saves;

        @Builder
        public response(Long id, String originFilename, String uploadedUrl, AttachStatus attachStatus, Map<String, AttachImageSave> saves) {
            this.id = id;
            this.originFilename = originFilename;
            this.uploadedUrl = uploadedUrl;
            this.attachStatus = attachStatus;
            this.saves = saves;
        }

        public static AttachImageDto.response createByAttachImage(AttachImage image, String uploadedUrl, AttachStatus attachStatus) {
            final Map<String, AttachImageSave> saves = image.getSaves().stream()
                    .map(AttachImageSaveEntity::getAttachImageSave)
                    .collect(Collectors.toMap(AttachImageSave::getImageKey, x -> x));

            return response.builder()
                    .id(image.getId())
                    .originFilename(image.getOriginFilename())
                    .uploadedUrl(uploadedUrl)
                    .attachStatus(attachStatus)
                    .saves(saves)
                    .build();


        }

    }

    @Getter
    public static class insert {
        private Long id;
        private String originFilename;
        private String uploadedUrl;
        private AttachStatus attachStatus;
        private Map<String, AttachImageSave> saves;

        public AttachImage toEntity() {

            List<AttachImageSaveEntity> attachImageSaveEntities = saves.keySet().stream()
                    .map(id -> saves.get(id))
                    .map(AttachImageSaveEntity::new)
                    .collect(Collectors.toList());

            return AttachImage.builder()
                    .saves(attachImageSaveEntities)
                    .originFilename(this.originFilename)
                    .build();

        }


    }
}
