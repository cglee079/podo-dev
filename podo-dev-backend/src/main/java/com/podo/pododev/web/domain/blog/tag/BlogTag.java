package com.podo.pododev.web.domain.blog.tag;

import com.podo.pododev.web.domain.BaseTimeEntity;
import com.podo.pododev.web.domain.blog.blog.Blog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "blog_tag")
@Entity
public class BlogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String tagValue;

    @CreatedDate
    private LocalDateTime createAt;

    public BlogTag(String tagValue) {
        this.tagValue = tagValue;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

}
