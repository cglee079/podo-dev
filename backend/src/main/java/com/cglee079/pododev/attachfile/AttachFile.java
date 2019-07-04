package com.cglee079.pododev.attachfile;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "article_seq")
    private long articleSeq;

    @Column
    private String filename;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "ext")
    private String ext;

    @Column(name = "create_at")
    private Date createAt;

}
