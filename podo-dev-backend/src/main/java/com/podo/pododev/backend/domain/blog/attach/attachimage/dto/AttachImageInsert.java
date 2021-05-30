package com.podo.pododev.backend.domain.blog.attach.attachimage.dto;

import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSave;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSaveEntity;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class AttachImageInsert {

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
