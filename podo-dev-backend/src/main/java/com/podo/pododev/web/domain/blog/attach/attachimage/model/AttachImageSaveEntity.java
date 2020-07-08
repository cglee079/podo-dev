package com.podo.pododev.web.domain.blog.attach.attachimage.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_image_save")
@Entity
public class AttachImageSaveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AttachImageSave attachImageSave;

    @Builder
    public AttachImageSaveEntity(AttachImageSave attachImageSave) {
        this.attachImageSave = attachImageSave;
    }
}
