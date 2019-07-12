package com.cglee079.pododev.blog.attachfile;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "blog_seq")
    private long blogSeq;

    @Column
    private String filename;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "ext")
    private String ext;

    @Column(name = "create_at")
    private Date createAt;

}
