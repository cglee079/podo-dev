package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.sun.javafx.beans.IDProperty;
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
@Table(name = "blog_image")
@Entity
public class AttachImage {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "blog_seq")
    private Long blogSeq;

    @Column(name = "origin_name")
    private String originName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_image_seq")
    List<AttachImageSave> saves;

    @Builder
    public AttachImage(Long blogSeq, String originName, List<AttachImageSave> saves) {
        this.blogSeq = blogSeq;
        this.originName = originName;
        this.saves = saves;
    }
}
