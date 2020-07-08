package com.podo.pododev.web.domain.blog.comment.model;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog_comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_by")
    private User writer;

    private String contents;

    private Integer childCount;

    private Long parentId;

    private Integer depth;

    private Long cgroup;

    private BigDecimal sort;

    private Boolean enabled;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public Comment(User writer, String contents, Long cgroup,
                   Long parentId, Integer depth, BigDecimal sort,
                   Boolean enabled, Integer childCount) {
        this.writer = writer;
        this.contents = contents;
        this.cgroup = cgroup;
        this.depth = depth;
        this.parentId = parentId;
        this.sort = sort;
        this.childCount = childCount;
        this.enabled = enabled;
    }

    public void changeCgroup(Long cgroup) {
        this.cgroup = cgroup;
    }

    public void changeBlog(Blog blog) {
        this.blog = blog;
    }

    public void increaseChildCount() {
        this.childCount++;
    }

    public void decreaseChildCount() {
        if (this.childCount <= 0) {
            childCount = 0;
            return;
        }

        this.childCount--;
    }

    public void erase(String deletedContents) {
        this.contents = deletedContents;
        this.enabled = false;
    }

    public boolean canDeleted() {
        return !this.enabled && this.childCount == 0;
    }

    public boolean isExceedMaxCommentDepth(Integer maxCommentDepth) {
        return (this.depth + 1) > maxCommentDepth;
    }

    public BigDecimal getChildCommentSort() {
        return BigDecimal.valueOf(childCount + 1)
                .divide(BigDecimal.valueOf(Math.pow(10, 3 * depth)))
                .add(sort);
    }

    public boolean isWrittenBy(Long userId) {
        return this.writer.getId().equals(userId);
    }


}
