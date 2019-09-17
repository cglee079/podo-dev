package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.comment.Comment;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog")
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String title;

    @Column
    private String contents;

    @Column(name = "hit_cnt")
    private Integer hitCnt;

    @Column
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_seq")
    private List<AttachImage> images;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_seq")
    private List<AttachFile> files;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_seq")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_seq")
    private List<Tag> tags;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateAt;


    @Builder
    public Blog(String title, String contents, Boolean enabled, List<Tag> tags, List<AttachImage> images, List<AttachFile> files) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.tags = tags;
        this.images = images;
        this.files = files;
        this.hitCnt = 0;
    }

    /**
     * 게시글 수정 시
     */
    public void update(String title, String contents, Boolean enabled) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
    }

    /**
     * Local Domain -> upload Server Domain
     *
     * @param localDomain
     * @param uploadServerDomain
     */
    public void updateContentSrc(String localDomain, String uploadServerDomain) {
        this.contents = this.contents.replace(localDomain, uploadServerDomain);
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void increaseHitCnt() {
        this.hitCnt++;
    }
}
