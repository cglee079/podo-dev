package com.cglee079.pododev.web.domain.blog.attachfile;

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
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "blog_seq")
    private Long blogSeq;

    @Column(name = "origin_key")
    private String originKey;

    @Column(name = "origin_name")
    private String originName;

    @Column
    private String filename;

    @Column
    private String path;

    @Column
    private Long filesize;

    @Column(name = "create_at")
    private Date createAt;

    @Builder
    public AttachFile(String originKey, Long blogSeq, String filename, String originName, String path, Long filesize) {
        this.originKey = originKey;
        this.blogSeq = blogSeq;
        this.filename = filename;
        this.originName = originName;
        this.path = path;
        this.filesize = filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }
}
