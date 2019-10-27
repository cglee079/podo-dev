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
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog")
@Entity
public class Blog extends UpdatableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private Integer hitCnt;

    private Boolean feeded;

    private Boolean enabled;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachImage> attachImages = new ArrayList<>();

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachFile> attachFiles = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<BlogTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Blog(List<AttachImage> attachImages, List<AttachFile> attachFiles,
                String title, String contents, Boolean enabled) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.attachImages = attachImages;
        this.attachFiles = attachFiles;
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

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    void addAttachImage(AttachImage attachImage) {
        this.attachImages.add(attachImage);
        attachImage.changeBlog(this);
    }

    public void removeAttachImage(Long imageId) {
        final Optional<AttachImage> attachImageOptional = this.attachImages.stream().filter(image -> image.getId().equals(imageId)).findFirst();

        if (attachImageOptional.isPresent()) {
            final AttachImage attachImage = attachImageOptional.get();
            this.attachImages.remove(attachImage);
            attachImage.changeBlog(null);
        }
    }

    public void addAttachFile(AttachFile attachFile) {
        this.attachFiles.add(attachFile);
        attachFile.changeBlog(this);
    }

    public void removeAttachFile(Long fileId) {
        final Optional<AttachFile> attachFileOptional = this.attachFiles.stream().filter(file -> file.getId().equals(fileId)).findFirst();

        if (attachFileOptional.isPresent()) {
            final AttachFile attachFile = attachFileOptional.get();
            this.attachFiles.remove(attachFile);
            attachFile.changeBlog(null);
        }
    }

}
