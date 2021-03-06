package com.podo.pododev.web.domain.blog.blog.model;

import com.podo.pododev.web.domain.BaseEntity;
import com.podo.pododev.web.domain.blog.attach.attachfile.model.AttachFile;
import com.podo.pododev.web.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.history.model.BlogHistory;
import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blog")
@Entity
public class Blog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private Integer hitCount;

    private Boolean webFeeded;

    private Boolean enabled;

    @CreatedDate
    private LocalDateTime publishAt;

    @OneToMany(mappedBy = "blog")
    private List<BlogHistory> histories = new ArrayList<>();

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
                String title, String contents, Boolean enabled, Boolean webFeeded, Integer hitCount) {
        this.title = title;
        this.contents = contents;
        this.enabled = enabled;
        this.attachImages = new ArrayList<>(attachImages);
        this.attachFiles = new ArrayList<>(attachFiles);
        this.hitCount = hitCount;
        this.webFeeded = webFeeded;
    }

    public void changeTitle(String title) {
        this.title = title;
        this.webFeeded = false;
    }

    public void changeContents(String contents) {
        this.contents = contents;
        this.webFeeded = false;
    }

    public void doWebFeeded() {
        this.webFeeded = true;
    }

    public void increaseHitCount() {
        this.hitCount++;
    }

    public void addTag(BlogTag tag) {
        this.tags.add(tag);
        tag.changeBlog(this);
    }

    public void clearTags() {
        for (BlogTag tag : tags) {
            tag.changeBlog(null);
        }
        this.tags.clear();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.changeBlog(this);
    }

    public void addAttachImage(AttachImage attachImage) {
        this.attachImages.add(attachImage);
        attachImage.changeBlog(this);
    }

    public void removeAttachImage(Long imageId) {
        final Optional<AttachImage> attachImageOptional = this.attachImages.stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst();

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
        final Optional<AttachFile> attachFileOptional = this.attachFiles.stream()
                .filter(file -> file.getId().equals(fileId))
                .findFirst();

        if (attachFileOptional.isPresent()) {
            final AttachFile attachFile = attachFileOptional.get();
            this.attachFiles.remove(attachFile);
            attachFile.changeBlog(null);
        }
    }

    public void updateStatus(BlogStatus status, LocalDateTime now) {
        switch (status) {
            case PUBLISH:
                this.publishAt = now;
                this.enabled = true;
                break;
            case VISIBLE:
                this.enabled = true;
                break;
            case INVISIBLE:
            default:
                this.enabled = false;
                break;
        }
    }

    public BlogHistory createHistory() {
        final BlogHistory blogHistory = BlogHistory.builder()
                .blog(this)
                .build();

        this.histories.add(blogHistory);
        return blogHistory;
    }


}
