package com.cglee079.pododev.article;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private String thumbnail;

    @Column(name = "create_at")
    private Date createAt;


    @Column(name = "hit_cnt")
    private long hitCnt;

    @Column
    private boolean enabled;

//    private List<AttachImage> images;
//    private List<AttachFile> files;

}
