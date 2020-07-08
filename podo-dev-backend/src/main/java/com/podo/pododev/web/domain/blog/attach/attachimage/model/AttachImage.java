package com.podo.pododev.web.domain.blog.attach.attachimage.model;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "blog_image")
@Entity
public class AttachImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String originFilename;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_image_id")
    List<AttachImageSaveEntity> saves = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public AttachImage(String originFilename, List<AttachImageSaveEntity> saves) {
        this.originFilename = originFilename;
        this.saves = saves;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

}
