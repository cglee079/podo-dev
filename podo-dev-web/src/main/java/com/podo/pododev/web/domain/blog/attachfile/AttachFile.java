package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_file")
@Getter
@Entity
public class AttachFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String originName;

    private String filename;

    private String path;

    private Long filesize;

    @Builder
    public AttachFile(String filename, String originName, String path, Long filesize) {
        this.filename = filename;
        this.originName = originName;
        this.path = path;
        this.filesize = filesize;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }
}
