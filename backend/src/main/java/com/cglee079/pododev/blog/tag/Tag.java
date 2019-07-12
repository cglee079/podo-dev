package com.cglee079.pododev.blog.tag;

import javax.persistence.*;

@Table(name = "blog_tag")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "blog_seq")
    private long blogSeq;

    @Column
    private String value;

    @Column
    private int order;
}
