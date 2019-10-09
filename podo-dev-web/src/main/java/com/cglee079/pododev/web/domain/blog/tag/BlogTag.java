package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.domain.BaseEntity;
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
public class BlogTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    private String val;

    private Integer idx;

    @Builder
    public BlogTag(Blog blog, String val, Integer idx) {
        this.blog = blog;
        this.val = val;
        this.idx = idx;
    }

    public void updateIdx(int i) {
        this.idx = i;
    }

}
