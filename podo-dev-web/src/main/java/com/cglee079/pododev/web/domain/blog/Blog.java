package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.UpdatableBaseEntity;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.comment.Comment;
import com.cglee079.pododev.web.domain.blog.tag.BlogTag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog")
@Entity
public class Blog extends UpdatableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String title;

    private String contents;

    private Integer hitCnt;

    private Boolean feeded;

    private Boolean enabled;

    @OneToMany(mappedBy = "blog")
    private List<AttachImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<AttachFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<BlogTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Blog(String title, String contents, Boolean enabled) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.hitCnt = 0;
        this.feeded = false;
    }

    /**
     * 게시글 수정 시
     */
    public void update(String title, String contents, Boolean enabled) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.feeded = false;
    }

    public void doFeeded() {
        this.feeded = true;
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

    public void addTag(BlogTag tag) {
        this.tags.add(tag);
    }

    public void addAttachImage(AttachImage image) {
        this.images.add(image);
    }

    public void removeAttachImage(AttachImage attachImage) {
        this.images.remove(attachImage);
    }

    public void addAttachFile(AttachFile file) {
        this.files.add(file);
    }

    public void removeAttachFile(AttachFile attachFile) {
        this.files.remove(attachFile);
    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
