package com.podo.pododev.web.domain.blog.history;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.blog.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_history")
@Entity
public class BlogHistory extends BaseEntity {

    @Id
    @Column(name = "history_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String title;
    private String contents;

    @Builder
    public BlogHistory(Blog blog) {
        this.blog = blog;
        this.title = blog.getTitle();
        this.contents = blog.getContents();

    }
}
