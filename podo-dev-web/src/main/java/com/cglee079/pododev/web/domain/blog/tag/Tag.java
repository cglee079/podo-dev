package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.domain.blog.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_tag")
@Entity
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    private String val;

    private Integer idx;

    @Builder
    public Tag(String val, Integer idx) {
        this.val = val;
        this.idx = idx;
    }

    public void updateIdx(int i) {
        this.idx = i;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
        blog.addTag(this);
    }
}
