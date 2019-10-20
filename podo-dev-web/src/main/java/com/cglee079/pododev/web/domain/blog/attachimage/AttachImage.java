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
import java.util.Objects;

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

    @OneToMany(mappedBy = "attachImage", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AttachImageSave> saves = new ArrayList<>();

    @Builder
    public AttachImage(List<AttachImageSave> saves, String originName) {
        this.saves = saves;
        this.originName = originName;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

}
