package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageDto;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String title;

    private String contents;

    private Integer hitCnt;

    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_seq")
    private List<AttachImage> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_seq")
    private List<AttachFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;


    @Builder
    public Blog(String title, String contents, Boolean enabled, List<AttachImage> images, List<AttachFile> files) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
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

    public void increaseHitCnt() {
        this.hitCnt++;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addImage(AttachImage image) {
        this.images.add(image);
    }

    public void removeImage(Long seq) {
        this.images.remove(this.images.stream().filter(image -> image.getSeq().equals(seq)).findFirst().get());
    }

    public void addFile(AttachFile file) {
        this.files.add(file);
    }

    public void removeFile(Long seq) {
        this.images.remove(this.images.stream().filter(image -> image.getSeq().equals(seq)).findFirst().get());
    }
}
