package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSavedDto;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

public class AttachImageDto {

    @Getter
    public static class response {
        private Long seq;
        private String key;
        private String originName;
        private String domainUrl;
        private FileStatus fileStatus;
        private Map<String, AttachImageSavedDto.response> saves;

        @Builder
        public response(String key, String originName, String domainUrl, FileStatus fileStatus, Map<String, AttachImageSavedDto.response> saves) {
            this.key = key;
            this.originName = originName;
            this.domainUrl = domainUrl;
            this.fileStatus = fileStatus;
            this.saves = saves;
        }

        public response(AttachImage image, String domainUrl, FileStatus fileStatus) {
            this.seq = image.getSeq();
            this.key = image.getOriginKey();
            this.originName = image.getOriginName();
            this.domainUrl = domainUrl;
            this.fileStatus = fileStatus;
            this.saves = new HashMap<>();

            image.getSaves().forEach(save -> {
                this.saves.put(save.getImageId(), new AttachImageSavedDto.response(save));
            });

        }

    }

    @Getter
    public static class insert {
        private String key;
        private String originName;
        private String domainUrl;
        private String fileStatus;
        private Map<String, AttachImageSavedDto.insert> saves;

        public AttachImage toEntity() {

            List<AttachImageSave> attachImageSaves = new LinkedList<>();

            Iterator<String> keys = saves.keySet().iterator();
            String key;
            AttachImageSavedDto.insert save;
            while (keys.hasNext()) {
                key = keys.next();
                save = saves.get(key);

                attachImageSaves.add(save.toEntity(key));
            }

            return AttachImage.builder()
                    .originKey(this.key)
                    .originName(this.originName)
                    .saves(attachImageSaves)
                    .build();
        }
    }
}
