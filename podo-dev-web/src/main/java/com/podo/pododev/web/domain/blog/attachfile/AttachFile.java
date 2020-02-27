package com.podo.pododev.web.domain.blog.attachfile;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.blog.Blog;
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

    //TODO DB바꾸자
    private String originFilename;

    private String filename;

    //TODO DB바꾸자
    private String filePath;

    private Long filesize;

    @Builder
    public AttachFile(String filename, String originFilename, String filePath, Long filesize) {
        this.filename = filename;
        this.originFilename = originFilename;
        this.filePath = filePath;
        this.filesize = filesize;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }
}
