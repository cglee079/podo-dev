package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.domain.BaseEntity;
import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.user.User;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_comment")
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_seq")
    private Blog blog;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private String contents;

    private Integer child;

    private Long parentSeq;

    private Integer depth;

    private Long cgroup;

    private Double sort;

    private Boolean enabled;

    @Builder
    public Comment(Blog blog, User user, String contents,
                   Long cgroup, Integer child, Long parentSeq, Integer depth, Double sort) {

        this.blog = blog;
        this.user = user;
        this.contents = contents;
        this.child = child;
        this.cgroup = cgroup;
        this.depth = depth;
        this.parentSeq = parentSeq;
        this.sort = sort;
        this.enabled = true;
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
