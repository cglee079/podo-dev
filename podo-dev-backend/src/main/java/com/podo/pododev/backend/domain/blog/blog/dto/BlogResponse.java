package com.podo.pododev.backend.domain.blog.blog.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.backend.domain.blog.attach.AttachStatus;
import com.podo.pododev.backend.domain.blog.attach.attachfile.dto.AttachFileResponse;
import com.podo.pododev.backend.domain.blog.attach.attachimage.dto.AttachImageResponse;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSave;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSaveEntity;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.history.dto.BlogHistoryResponses;
import com.podo.pododev.backend.domain.blog.tag.dto.BlogTagResponse;
import com.podo.pododev.backend.global.util.HtmlDocumentUtil;
import com.podo.pododev.backend.global.util.MarkdownUtil;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Getter
public class BlogResponse {

    private Long id;
    private String title;
    private String description;
    private String contents;
    private Integer hitCount;
    private String thumbnail;
    private Boolean enabled;
    private String publishAt;
    private List<BlogRelates> relates;
    private List<BlogTagResponse> tags;
    private List<AttachImageResponse> attachImages;
    private List<AttachFileResponse> attachFiles;
    private List<BlogHistoryResponses> histories;
    private Integer commentCount;
    private Long beforeBlogId;
    private Long nextBlogId;
    private String createAt;
    private String updateAt;

    public BlogResponse(Blog blog, Blog beforeBlog, Blog nextBlog, List<Blog> relates, String uploaderDomain, AttachStatus attachStatus) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.description = HtmlDocumentUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
        this.contents = blog.getContents();
        this.hitCount = blog.getHitCount();
        this.publishAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
        this.updateAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
        this.commentCount = blog.getComments().size();
        this.enabled = blog.getEnabled();

        this.beforeBlogId = Objects.nonNull(beforeBlog) ? beforeBlog.getId() : null;
        this.nextBlogId = Objects.nonNull(nextBlog) ? nextBlog.getId() : null;

        this.relates = relates.stream()
                .map(BlogRelates::new)
                .collect(toList());

        this.tags = blog.getTags().stream()
                .map(BlogTagResponse::new)
                .collect(toList());

        this.attachImages = blog.getAttachImages().stream()
                .map(image -> AttachImageResponse.createByAttachImage(image, uploaderDomain, attachStatus))
                .collect(toList());

        this.attachFiles = blog.getAttachFiles().stream()
                .map(file -> AttachFileResponse.createByAttachFile(file, uploaderDomain, attachStatus))
                .collect(toList());

        this.histories = blog.getHistories().stream()
                .map(BlogHistoryResponses::new)
                .collect(toList());
        Collections.reverse(this.histories);

        if (!attachImages.isEmpty()) {
            List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                    .map(AttachImageSaveEntity::getAttachImageSave)
                    .collect(toList());

            final Optional<AttachImageSave> thumbnailSaveOptional = saves.stream().filter(s -> s.getImageKey().equals("origin")).findFirst();
            if (thumbnailSaveOptional.isPresent()) {
                AttachImageSave thumbnailSave = thumbnailSaveOptional.get();
                this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getFilePath(), thumbnailSave.getFilename());
            }
        }

    }
}
