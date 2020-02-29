package com.podo.pododev.web.domain.blog.comment;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.user.User;
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
@Table(name = "blog_comment")
@Entity
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    //TODO
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_by2")
    private User writer;

    private String contents;

    private Integer childCount;

    private Long parentId;

    private Integer depth;

    private Long cgroup;

    private Double sort;

    private Boolean enabled;

    private Boolean byAdmin;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public Comment(Blog blog, User writer, String contents, Long cgroup,
                   Integer childCount, Long parentId, Integer depth, Double sort,
                   Boolean byAdmin) {

        this.blog = blog;
        this.writer = writer;
        this.contents = contents;
        this.childCount = childCount;
        this.cgroup = cgroup;
        this.depth = depth;
        this.parentId = parentId;
        this.sort = sort;
        this.byAdmin = byAdmin;
        this.enabled = true;
    }

    public void changeCgroup(Long id) {
        this.cgroup = id;
    }

    public void increaseChildCount() {
        this.childCount++;
    }

    public void decreaseChildCount() {
        this.childCount--;
    }

    public void erase() {
        this.contents = "삭제된 댓글입니다.";
        this.enabled = false;
    }

    public boolean isErase() {
        return !this.enabled;
    }

    public boolean isExceedMaxCommentDepth(Integer maxCommentDepth) {
        return (this.depth + 1) > maxCommentDepth;
    }

    public double getChildCommentSort() {
        return ((double) (childCount + 1) / Math.pow(10, 3 * depth)) + sort;
    }

    public boolean isWrittenBy(Long userId) {
        return this.writer.getId().equals(userId);
    }

    public boolean hasChild() {
        return childCount > 0;
    }
}
