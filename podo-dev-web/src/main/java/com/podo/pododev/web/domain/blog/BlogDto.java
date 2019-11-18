package com.podo.pododev.web.domain.blog;

import com.podo.pododev.web.domain.blog.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.podo.pododev.web.domain.blog.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.podo.pododev.web.domain.blog.attachimage.save.AttachImageSaveEntity;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.BlogTagDto;
import com.podo.pododev.core.util.FormatUtil;
import com.podo.pododev.core.util.MarkdownUtil;
import com.podo.pododev.core.util.PathUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        private List<AttachImageDto.insert> images;
        private List<AttachFileDto.insert> files;

        public Blog toEntity() {
            //Images to Entity
            List<AttachImage> images = this.images.stream()
                    .filter(image -> image.getFileStatus() == FileStatus.NEW)
                    .map(AttachImageDto.insert::toEntity)
                    .collect(Collectors.toList());

            //File to Entity
            List<AttachFile> files = this.files.stream()
                    .filter(image -> image.getFileStatus() == FileStatus.NEW)
                    .map(AttachFileDto.insert::toEntity)
                    .collect(Collectors.toList());

            final Blog blog = Blog.builder()
                    .attachImages(images)
                    .attachFiles(files)
                    .title(title)
                    .contents(contents)
                    .enabled(status != BlogStatus.INVISIBLE)
                    .build();

            for (AttachImage image : images) {
                image.changeBlog(blog);
            }

            for (AttachFile file : files) {
                file.changeBlog(blog);
            }

            return blog;

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

        private List<AttachImageDto.insert> images;
        private List<AttachFileDto.insert> files;
    }

    @Setter
    @Getter
    public class request {
        private Integer page;
        private String tag;
        private String search;
    }


    @Getter
    public static class response {
        private Long id;
        private String title;
        private String desc;
        private String contents;
        private Integer hitCnt;
        private String thumbnail;
        private Boolean enabled;
        private String publishAt;
        private List<BlogDto.relates> relates;
        private List<BlogTagDto.response> tags;
        private List<AttachImageDto.response> images;
        private List<AttachFileDto.response> files;
        private Integer commentCnt;
        private Long before;
        private Long next;
        private String createAt;
        private String updateAt;

        public response(Blog blog, Blog before, Blog next, List<Blog> relates, String uploaderDomain, FileStatus fileStatus) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.desc = MarkdownUtil.escape(MarkdownUtil.extractPlainText(blog.getContents()));
            this.contents = blog.getContents();
            this.hitCnt = blog.getHitCnt();
            this.publishAt = FormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
            this.createAt = FormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
            this.updateAt = FormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
            this.commentCnt = blog.getComments().size();
            this.enabled = blog.getEnabled();
            this.before = !Objects.isNull(before) ? before.getId() : null;
            this.next = !Objects.isNull(next) ? next.getId() : null;
            this.relates = relates.stream()
                    .map(BlogDto.relates::new)
                    .collect(Collectors.toList());

            this.tags = blog.getTags().stream()
                    .map(BlogTagDto.response::new)
                    .collect(Collectors.toList());

            this.images = blog.getAttachImages().stream()
                    .map(image -> new AttachImageDto.response(image, uploaderDomain, fileStatus))
                    .collect(Collectors.toList());

            this.files = blog.getAttachFiles().stream()
                    .map(file -> new AttachFileDto.response(file, uploaderDomain, fileStatus))
                    .collect(Collectors.toList());


            if (!images.isEmpty()) {
                List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                        .map(AttachImageSaveEntity::getAttachImageSave)
                        .collect(Collectors.toList());

                Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageId().equals("origin")).findFirst();
                if (thumbnailSaveOpt.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                    this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getPath(), thumbnailSave.getFilename());
                }
            }

        }

    }

    @Getter
    public static class responseList {
        private Long id;
        private String thumbnail;
        private String title;
        private String desc;
        private String createAt;
        private String publishAt;
        private String updateAt;
        private Integer commentCnt;
        private List<BlogTagDto.response> tags;
        private Boolean enabled;

        public responseList(Blog blog, String uploaderDomain) {
            this.id = blog.getId();
            this.title = blog.getTitle();
            this.desc = MarkdownUtil.escape(MarkdownUtil.extractPlainText(blog.getContents()));
            this.createAt = FormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
            this.publishAt = FormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
            this.updateAt = FormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
            this.enabled = blog.getEnabled();
            this.commentCnt = blog.getComments().size();

            List<AttachImage> images = blog.getAttachImages();
            if (!images.isEmpty()) {
                List<AttachImageSave> saves = blog.getAttachImages().get(0).getSaves().stream()
                        .map(AttachImageSaveEntity::getAttachImageSave)
                        .collect(Collectors.toList());

                Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageId().equals("origin")).findFirst();
                if (thumbnailSaveOpt.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                    this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getPath(), thumbnailSave.getFilename());
                }
            }

            this.tags = blog.getTags().stream()
                    .map(BlogTagDto.response::new)
                    .collect(Collectors.toList());

        }

        public responseList(Blog blog, String desc, String uploadServerDomain) {
            this(blog, uploadServerDomain);
            this.desc = desc;
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
            this.createAt = FormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
            this.updateAt = FormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
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
            this.publishAt = FormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
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
            this.desc = MarkdownUtil.escape(MarkdownUtil.extractPlainText(blog.getContents()));
            this.publishAt = blog.getPublishAt();
            this.createAt = blog.getCreateAt();
            this.updateAt = blog.getUpdateAt();
            this.enabled = blog.getEnabled();
            this.contentHtml = MarkdownUtil.toHtml(blog.getContents());
            this.tags = blog.getTags().stream().map(BlogTag::getVal).collect(Collectors.toList());
        }

    }
}
