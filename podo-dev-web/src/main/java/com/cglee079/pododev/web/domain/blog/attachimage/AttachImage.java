package com.cglee079.pododev.web.domain.blog.attachimage;

import com.cglee079.pododev.web.domain.BaseEntity;
import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String originName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_image_id")
    List<AttachImageSaveEntity> saves = new ArrayList<>();

    @Builder
    public AttachImage(List<AttachImageSaveEntity> saves, String originName) {
        this.saves = saves;
        this.originName = originName;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

}
