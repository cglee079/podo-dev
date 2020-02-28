package com.podo.pododev.web.domain.blog.attachimage;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.attachimage.vo.AttachImageSaveEntity;
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

    //TODO
    private String originFilename;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_image_id")
    List<AttachImageSaveEntity> saves = new ArrayList<>();

    @Builder
    public AttachImage(String originFilename, List<AttachImageSaveEntity> saves) {
        this.saves = saves;
        this.originFilename = originFilename;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

}
