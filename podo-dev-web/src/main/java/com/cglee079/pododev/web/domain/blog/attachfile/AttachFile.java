package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.web.domain.BaseEntity;
import com.cglee079.pododev.web.domain.blog.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_file")
@Getter
@Entity
public class AttachFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    private String originName;

    private String filename;

    private String path;

    private Long filesize;

    @Builder
    public AttachFile(Blog blog, String filename, String originName, String path, Long filesize) {
        this.blog = blog;
        this.filename = filename;
        this.originName = originName;
        this.path = path;
        this.filesize = filesize;
    }

}
