package com.cglee079.pododev.article.tag;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(name = "artilce_seq")
    private long articleSeq;

    @Column
    private String value;

    @Column
    private int order;
}
