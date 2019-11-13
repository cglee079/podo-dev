package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttachImageDto {

    @Getter
    public static class upload {
        private String url;
        private String base64;
    }

    @Getter
    public static class response {
        private Long id;
        private String originName;
        private String uploadedUrl;
        private FileStatus fileStatus;
        private Map<String, AttachImageSave> saves;

        @Builder
        public response(String originName, String uploadedUrl, FileStatus fileStatus, Map<String, AttachImageSave> saves) {
            this.originName = originName;
            this.uploadedUrl = uploadedUrl;
            this.fileStatus = fileStatus;
            this.saves = saves;
        }

        public response(AttachImage image, String uploadedUrl, FileStatus fileStatus) {
            this.id = image.getId();
            this.originName = image.getOriginName();
            this.uploadedUrl = uploadedUrl;
            this.fileStatus = fileStatus;
            this.saves = image.getSaves().stream()
                    .map(AttachImageSaveEntity::getAttachImageSave)
                    .collect(Collectors.toMap(AttachImageSave::getImageId, Function.identity()));

        }

    }

    @Getter
    public static class insert {
        private Long id;
        private String originName;
        private String uploadedUrl;
        private String fileStatus;
        private Map<String, AttachImageSave> saves;

        public AttachImage toEntity() {

            List<AttachImageSaveEntity>  attachImageSaveEntities  = saves.keySet().stream()
                    .map( id -> saves.get(id))
                    .map(AttachImageSaveEntity::new)
                    .collect(Collectors.toList());

            return AttachImage.builder()
                    .saves(attachImageSaveEntities)
                    .originName(this.originName)
                    .build();

        }


    }
}
