package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.BaseEntity;
import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_image")
@Entity
public class AttachImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    private String originName;

    @OneToMany(mappedBy = "attachImage")
    List<AttachImageSave> saves = new ArrayList<>();

    @Builder
    public AttachImage(Blog blog, String originName) {
        this.blog = blog;
        this.originName = originName;
    }

    public void addImageSave(AttachImageSave attachImageSave) {
        this.saves.add(attachImageSave);
    }

    public void removeImageSave(AttachImageSave save) {
        this.saves.remove(save);
    }
}
