package com.podo.pododev.web.domain.blog.blog.dto;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import com.podo.pododev.web.global.util.HtmlDocumentUtil;
import com.podo.pododev.web.global.util.MarkdownUtil;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class BlogFeed {
    private Long id;
    private String desc;
    private String contentHtml;
    private String title;
    private List<String> tags;
    private LocalDateTime publishAt;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean enabled;

    public BlogFeed(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.desc = HtmlDocumentUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
        this.publishAt = blog.getPublishAt();
        this.createAt = blog.getCreateAt();
        this.updateAt = blog.getUpdateAt();
        this.enabled = blog.getEnabled();
        this.contentHtml = MarkdownUtil.toHtml(blog.getContents());
        this.tags = blog.getTags().stream().map(BlogTag::getTagValue).collect(toList());
    }
}
