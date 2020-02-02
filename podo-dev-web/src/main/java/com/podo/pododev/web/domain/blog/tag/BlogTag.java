package com.podo.pododev.web.domain.blog.tag;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.Blog;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String tagValue;

    private Integer tagSort;

    public BlogTag(Blog blog, String tagValue) {
        this.blog = blog;
        this.tagValue = tagValue;
    }

    public void changeIndex(int tagSort) {
        this.tagSort = tagSort;
    }

}
