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

    private String originName;

    private String filename;

    private String path;

    private Long filesize;

    private Date createAt;

    @Builder
    public AttachFile(String filename, String originName, String path, Long filesize) {
        this.filename = filename;
        this.originName = originName;
        this.path = path;
        this.filesize = filesize;
    }
}
