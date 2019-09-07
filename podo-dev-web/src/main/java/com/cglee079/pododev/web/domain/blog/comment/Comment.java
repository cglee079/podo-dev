package com.cglee079.pododev.web.domain.blog.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "blog_Seq")
    private Long blogSeq;

    @Column
    private String username;

    @Column(name = "user_id")
    private String userId;

    @Column
    private String contents;

    @Column
    private Integer child;

    @Column(name = "parent_seq")
    private Long parentSeq;

    @Column
    private Integer depth;

    @Column
    private Long cgroup;

    @Column
    private Double sort;

    @Column
    private Boolean enabled;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;


    @Builder
    public Comment(Long blogSeq, String username, String userId, String contents,
                   Long cgroup, Integer child, Long parentSeq, Integer depth, Double sort) {
        this.blogSeq = blogSeq;
        this.username = username;
        this.userId = userId;
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
