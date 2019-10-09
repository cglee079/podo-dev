package com.cglee079.pododev.web.domain.blog.attachimage.save;

import com.cglee079.pododev.web.domain.BaseEntity;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_image_save")
@Entity
public class AttachImageSave extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_image_seq")
    private AttachImage attachImage;

    private String imageId;

    private String filename;

    private String path;

    private Integer width;

    private Integer height;

    private Long filesize;

    @Builder
    public AttachImageSave(AttachImage attachImage, String imageId, String filename, String path, Integer width, Integer height, Long filesize) {
        this.attachImage = attachImage;
        this.filename = filename;
        this.imageId = imageId;
        this.path = path;
        this.width = width;
        this.height = height;
        this.filesize = filesize;
    }

}
