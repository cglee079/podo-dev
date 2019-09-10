package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveDto;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

public class AttachImageDto {

    @Getter
    public static class upload {
        private String url;
        private String base64;
    }

    @Getter
    public static class response {
        private Long seq;
        private String originName;
        private String domainUrl;
        private FileStatus fileStatus;
        private Map<String, AttachImageSaveDto.response> saves;

        @Builder
        public response(String originName, String domainUrl, FileStatus fileStatus, Map<String, AttachImageSaveDto.response> saves) {
            this.originName = originName;
            this.domainUrl = domainUrl;
            this.fileStatus = fileStatus;
            this.saves = saves;
        }

        public response(AttachImage image, String domainUrl, FileStatus fileStatus) {
            this.seq = image.getSeq();
            this.originName = image.getOriginName();
            this.domainUrl = domainUrl;
            this.fileStatus = fileStatus;
            this.saves = new HashMap<>();

            image.getSaves().forEach(save -> {
                this.saves.put(save.getImageId(), new AttachImageSaveDto.response(save));
            });

        }

    }

    @Getter
    public static class insert {
        private String originName;
        private String domainUrl;
        private String fileStatus;
        private Map<String, AttachImageSaveDto.insert> saves;

        public AttachImage toEntity() {

            List<AttachImageSave> attachImageSaves = new LinkedList<>();

            Iterator<String> keys = saves.keySet().iterator();
            String key;
            AttachImageSaveDto.insert save;
            while (keys.hasNext()) {
                key = keys.next();
                save = saves.get(key);

                attachImageSaves.add(save.toEntity(key));
            }

            return AttachImage.builder()
                    .originName(this.originName)
                    .saves(attachImageSaves)
                    .build();
        }
    }

    @Getter
    public static class update {
        private Long seq;
        private String originName;
        private String domainUrl;
        private String fileStatus;
        private Map<String, AttachImageSaveDto.update> saves;

        public AttachImage toEntity(Long blogSeq) {
            List<AttachImageSave> attachImageSaves = new LinkedList<>();

            Iterator<String> keys = saves.keySet().iterator();
            String key;
            AttachImageSaveDto.update save;
            while (keys.hasNext()) {
                key = keys.next();
                save = saves.get(key);

                attachImageSaves.add(save.toEntity(key));
            }

            return AttachImage.builder()
                    .blogSeq(blogSeq)
                    .originName(this.originName)
                    .saves(attachImageSaves)
                    .build();
        }
    }
}
