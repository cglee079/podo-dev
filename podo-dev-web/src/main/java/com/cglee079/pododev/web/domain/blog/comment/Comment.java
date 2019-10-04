package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.domain.blog.Blog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    private String username;

    private String userId;

    private String contents;

    private Integer child;

    private Long parentSeq;

    private Integer depth;

    private Long cgroup;

    private Double sort;

    private Boolean enabled;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Builder
    public Comment(Blog blog, String username, String userId, String contents,
                   Long cgroup, Integer child, Long parentSeq, Integer depth, Double sort) {


        this.username = username;
        this.userId = userId;
        this.contents = contents;
        this.child = child;
        this.cgroup = cgroup;
        this.depth = depth;
        this.parentSeq = parentSeq;
        this.sort = sort;
        this.enabled = true;

        this.changeBlog(blog);
    }

    private void changeBlog(Blog blog) {
        this.blog = blog;
        blog.getComments().add(this);
    }

    public void updateCgroup(Long seq) {
        this.cgroup = seq;
    }

    public void increaseChild() {
        this.child++;
    }

    public void erase() {
        this.contents = "삭제된 댓글입니다.";
        this.enabled = false;
    }

    public void decreaseChild() {
        this.child--;
    }

    public boolean isErase() {
        return !this.enabled;
    }
}
