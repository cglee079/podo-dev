package com.cglee079.pododev.web.domain.blog.attachfile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(name = "blog_seq")
    private Long blogSeq;

    @Column
    private String filename;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "ext")
    private String ext;

    @Column(name = "create_at")
    private Date createAt;

}
