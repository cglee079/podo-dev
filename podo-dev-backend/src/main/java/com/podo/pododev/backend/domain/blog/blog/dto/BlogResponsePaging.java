package com.podo.pododev.backend.domain.blog.blog.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.core.util.PathUtil;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImage;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSave;
import com.podo.pododev.backend.domain.blog.attach.attachimage.model.AttachImageSaveEntity;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.tag.dto.BlogTagResponse;
import com.podo.pododev.backend.global.util.HtmlDocumentUtil;
import com.podo.pododev.backend.global.util.MarkdownUtil;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Getter
public class BlogResponsePaging {
    private Long id;
    private String thumbnail;
    private String title;
    private String description;
    private String createAt;
    private String publishAt;
    private String updateAt;
    private Integer commentCount;
    private List<BlogTagResponse> tags;
    private Boolean enabled;

    public BlogResponsePaging(Blog blog, String uploaderDomain) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.description = HtmlDocumentUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
        this.publishAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
        this.updateAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
        this.enabled = blog.getEnabled();
        this.commentCount = blog.getComments().size();

        this.tags = blog.getTags().stream()
                .map(BlogTagResponse::new)
                .collect(toList());

        final List<AttachImage> attachImages = blog.getAttachImages();
        if (!attachImages.isEmpty()) {
            final List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                    .map(AttachImageSaveEntity::getAttachImageSave)
                    .collect(toList());

            final Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageKey().equals("origin")).findFirst();
            if (thumbnailSaveOpt.isPresent()) {
                AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getFilePath(), thumbnailSave.getFilename());
            }
        }


    }

    public static BlogResponsePaging createWithHighlightDescription(Blog blog, String highlightDescription, String uploadServerDomain) {
        final BlogResponsePaging responsePaging = new BlogResponsePaging(blog, uploadServerDomain);
        responsePaging.description = highlightDescription;
        return responsePaging;
    }
}
