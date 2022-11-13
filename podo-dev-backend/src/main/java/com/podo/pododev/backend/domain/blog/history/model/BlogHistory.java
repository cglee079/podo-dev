package com.podo.pododev.backend.domain.blog.history.model;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_history")
@Entity
public class BlogHistory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String title;
    private String contents;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public BlogHistory(Blog blog) {
        this.blog = blog;
        this.title = blog.getTitle();
        this.contents = blog.getContents();
    }

    public void changeContents(String dd) {
        this.contents = dd;
    }
}
