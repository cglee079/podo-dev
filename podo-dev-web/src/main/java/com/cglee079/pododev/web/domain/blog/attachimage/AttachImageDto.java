package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.FileStatus;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveDto;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

public class AttachImageDto {

    //Url or base64;
    @Getter
    public static class upload {
        private String url;
        private String base64;
    }

    @Getter
    public static class response {
        private Long seq;
        private String originName;
        private String uploadedUrl;
        private FileStatus fileStatus;
        private Map<String, AttachImageSaveDto.response> saves;

        @Builder
        public response(String originName, String uploadedUrl, FileStatus fileStatus, Map<String, AttachImageSaveDto.response> saves) {
            this.originName = originName;
            this.uploadedUrl = uploadedUrl;
            this.fileStatus = fileStatus;
            this.saves = saves;
        }

        public response(AttachImage image, String uploadedUrl, FileStatus fileStatus) {
            this.seq = image.getSeq();
            this.originName = image.getOriginName();
            this.uploadedUrl = uploadedUrl;
            this.fileStatus = fileStatus;
            this.saves = new HashMap<>();

            image.getSaves().forEach(save -> {
                this.saves.put(save.getImageId(), new AttachImageSaveDto.response(save));
            });

        }

    }

    @Getter
    public static class insert {
        private Long seq;
        private String originName;
        private String uploadedUrl;
        private String fileStatus;
        private Map<String, AttachImageSaveDto.insert> saves;

        public AttachImage toEntity() {

            List<AttachImageSave> attachImageSaves = new LinkedList<>();

            saves.keySet().stream().forEach(key ->
                    attachImageSaves.add(saves.get(key).toEntity(key))
            );

            return AttachImage.builder()
                    .originName(this.originName)
                    .saves(attachImageSaves)
                    .build();
        }
    }
}
