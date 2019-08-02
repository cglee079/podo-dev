package com.cglee079.pododev.domain.blog.tag;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_tag")
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "blog_seq")
    private Long blogSeq;

    @Column
    private String val;

    @Column
    private Integer idx;

    @Builder
    public Tag(Long blogSeq, String val, Integer idx) {
        this.blogSeq = blogSeq;
        this.val = val;
        this.idx = idx;
    }
}
