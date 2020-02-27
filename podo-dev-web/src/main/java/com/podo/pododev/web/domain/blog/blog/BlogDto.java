package com.podo.pododev.web.domain.blog.blog;

import com.podo.pododev.core.util.MyHtmlUtil;
import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import com.podo.pododev.web.domain.blog.history.BlogHistoryDto;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.BlogTagDto;
import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.core.util.MarkdownUtil;
import com.podo.pododev.core.util.MyPathUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class BlogDto {

    @Getter
    public static class insert {
        @NotEmpty(message = "제목을 입력해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        @NotNull(message = "공개여부를 입력해주세요")
        private BlogStatus status;

        @Size(min = 1, message = "태그를 최소 1개를 입력해주세요")
        private List<BlogTagDto.insert> tags;
        private List<AttachImageDto.insert> attachImages;
        private List<AttachFileDto.insert> attachFiles;

        public Blog toEntity() {
            final List<AttachImage> attachImages = this.attachImages.stream()
                    .filter(image -> image.getAttachStatus() == AttachStatus.NEW)
                    .map(AttachImageDto.insert::toEntity)
                    .collect(toList());

            final List<AttachFile> attachFiles = this.attachFiles.stream()
                    .filter(image -> image.getAttachStatus() == AttachStatus.NEW)
                    .map(AttachFileDto.insert::toEntity)
                    .collect(toList());

            final Blog newBlog = Blog.builder()
                    .attachImages(attachImages)
                    .attachFiles(attachFiles)
                    .title(title)
                    .contents(contents)
                    .enabled(status != BlogStatus.INVISIBLE)
                    .build();

            for (AttachImage image : attachImages) {
                image.changeBlog(newBlog);
            }

            for (AttachFile file : attachFiles) {
                file.changeBlog(newBlog);
            }

            return newBlog;

        }
    }

    @Getter
    public static class update {
        @NotEmpty(message = "제목을 입력해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        @NotNull(message = "공개여부를 입력해주세요")
        private BlogStatus status;

        @Size(min = 1, message = "태그를 최소 1개를 입력해주세요")
        private List<BlogTagDto.insert> tags;

        private List<AttachImageDto.insert> attachImages;
        private List<AttachFileDto.insert> attachFiles;
    }

    @Setter
    @Getter
    @EqualsAndHashCode
    public class requestPaging {
        private Integer page;
        private String tag;
        private String search;
    }


    @Getter
    public static class response {
        private Long id;
        private String title;
        private String description;
        private String contents;
        private Integer hitCount;
        private String thumbnail;
        private Boolean enabled;
        private String publishAt;
        private List<BlogDto.relates> relates;
        private List<BlogTagDto.response> tags;
        private List<AttachImageDto.response> attachImages;
        private List<AttachFileDto.response> attachFiles;
        private List<BlogHistoryDto.responses> histories;
        private Integer commentCount;
        private Long beforeBlogId;
        private Long nextBlogId;
        private String createAt;
        private String updateAt;

        public response(Blog blog, Blog beforeBlog, Blog nextBlog, List<Blog> relates, String uploaderDomain, AttachStatus attachStatus) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.description = MyHtmlUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
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
                    .map(BlogDto.relates::new)
                    .collect(toList());

            this.tags = blog.getTags().stream()
                    .map(BlogTagDto.response::new)
                    .collect(toList());

            this.attachImages = blog.getAttachImages().stream()
                    .map(image -> AttachImageDto.response.createByAttachImage(image, uploaderDomain, attachStatus))
                    .collect(toList());

            this.attachFiles = blog.getAttachFiles().stream()
                    .map(file -> AttachFileDto.response.createByAttachFile(file, uploaderDomain, attachStatus))
                    .collect(toList());

            this.histories = blog.getHistories().stream()
                    .map(BlogHistoryDto.responses::new)
                    .collect(toList());
            Collections.reverse(this.histories);

            if (!attachImages.isEmpty()) {
                List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                        .map(AttachImageSaveEntity::getAttachImageSave)
                        .collect(toList());

                final Optional<AttachImageSave> thumbnailSaveOptional = saves.stream().filter(s -> s.getImageKey().equals("origin")).findFirst();
                if (thumbnailSaveOptional.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOptional.get();
                    this.thumbnail = MyPathUtils.merge(uploaderDomain, thumbnailSave.getFilePath(), thumbnailSave.getFilename());
                }
            }

        }

    }

    @Getter
    public static class responsePaging {
        private Long id;
        private String thumbnail;
        private String title;
        private String description;
        private String createAt;
        private String publishAt;
        private String updateAt;
        private Integer commentCount;
        private List<BlogTagDto.response> tags;
        private Boolean enabled;

        public responsePaging(Blog blog, String uploaderDomain) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.description = MyHtmlUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
            this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
            this.publishAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
            this.updateAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
            this.enabled = blog.getEnabled();
            this.commentCount = blog.getComments().size();

            this.tags = blog.getTags().stream()
                    .map(BlogTagDto.response::new)
                    .collect(toList());

            final List<AttachImage> attachImages = blog.getAttachImages();
            if (!attachImages.isEmpty()) {
                final List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                        .map(AttachImageSaveEntity::getAttachImageSave)
                        .collect(toList());

                final Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageKey().equals("origin")).findFirst();
                if (thumbnailSaveOpt.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                    this.thumbnail = MyPathUtils.merge(uploaderDomain, thumbnailSave.getFilePath(), thumbnailSave.getFilename());
                }
            }


        }

        public static responsePaging createWithHighlightDescription(Blog blog, String highlightDescription, String uploadServerDomain) {
            final responsePaging responsePaging = new responsePaging(blog, uploadServerDomain);
            responsePaging.description = highlightDescription;

            return responsePaging;
        }

    }

    @Getter
    public static class relates {
        private Long id;
        private String title;
        private Integer commentCount;
        private String createAt;
        private String updateAt;

        public relates(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.commentCount = blog.getComments().size();
            this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
            this.updateAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
        }
    }

    @Getter
    public static class archive {
        private Long id;
        private String title;
        private String publishAt;
        private Boolean enabled;

        public archive(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.publishAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
            this.enabled = blog.getEnabled();
        }
    }

    @Getter
    public static class feed {
        private Long id;
        private String desc;
        private String contentHtml;
        private String title;
        private List<String> tags;
        private LocalDateTime publishAt;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private Boolean enabled;

        public feed(Blog blog) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.desc = MyHtmlUtil.escapeHtml(MarkdownUtil.extractPlainText(blog.getContents()));
            this.publishAt = blog.getPublishAt();
            this.createAt = blog.getCreateAt();
            this.updateAt = blog.getUpdateAt();
            this.enabled = blog.getEnabled();
            this.contentHtml = MarkdownUtil.toHtml(blog.getContents());
            this.tags = blog.getTags().stream().map(BlogTag::getTagValue).collect(toList());
        }

    }
}
