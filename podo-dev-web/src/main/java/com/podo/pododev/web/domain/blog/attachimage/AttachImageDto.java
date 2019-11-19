package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
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
        private String originName;
        private String uploadedUrl;
        private AttachStatus attachStatus;
        private Map<String, AttachImageSave> saves;

        @Builder
        public response(Long id, String originName, String uploadedUrl, AttachStatus attachStatus, Map<String, AttachImageSave> saves) {
            this.id = id;
            this.originName = originName;
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
                    .originName(image.getOriginName())
                    .uploadedUrl(uploadedUrl)
                    .attachStatus(attachStatus)
                    .saves(saves)
                    .build();


        }

    }

    @Getter
    public static class insert {
        private Long id;
        private String originName;
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
                    .originName(this.originName)
                    .build();

        }


    }
}
