package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.web.domain.blog.attachfile.AttachFile;
import com.cglee079.pododev.web.domain.blog.attachfile.AttachFileDto;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImage;
import com.cglee079.pododev.web.domain.blog.attachimage.AttachImageDto;
import com.cglee079.pododev.web.domain.blog.attachimage.save.AttachImageSave;
import com.cglee079.pododev.web.domain.blog.tag.Tag;
import com.cglee079.pododev.web.domain.blog.tag.TagDto;
import com.cglee079.pododev.web.global.util.Formatter;
import com.cglee079.pododev.web.global.util.MarkdownUtil;
import com.cglee079.pododev.web.global.util.PathUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BlogDto {

    @Getter
    public static class insert {
        @NotEmpty(message = "제목을 입력해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        @NotNull(message = "공개여부를 입력해주세요")
        private Boolean enabled;

        @Size(min = 1, message = "태그를 최소 1개를 입력해주세요")
        private List<TagDto.insert> tags;
        private List<AttachImageDto.insert> images;
        private List<AttachFileDto.insert> files;

        public Blog toEntity() {
            //Tag To Entity
            List<Tag> tags = new LinkedList<>();
            this.tags.forEach(tag -> tags.add(tag.toEntity()));
            int idx = 1;
            for (Tag tag : tags) { //Redefine Order
                tag.updateIdx(idx++);
            }

            //Images to Entity
            List<AttachImage> images = new LinkedList<>();
            this.images.forEach(image -> {
                switch (FileStatus.valueOf(image.getFileStatus())) {
                    case NEW:
                        images.add(image.toEntity());
                        break;
                    case UNNEW:
                    case REMOVE:
                    case BE:
                    default:
                        break;
                }
            });

            //File to Entity
            List<AttachFile> files = new LinkedList<>();
            this.files.forEach(file -> {
                switch (FileStatus.valueOf(file.getFileStatus())) {
                    case NEW:
                        files.add(file.toEntity());
                        break;
                    case UNNEW:
                    case REMOVE:
                    case BE:
                    default:
                        break;
                }
            });

            return Blog.builder()
                    .title(title)
                    .contents(contents)
                    .enabled(enabled)
                    .images(images)
                    .files(files)
                    .tags(tags)
                    .build();
        }
    }

    @Getter
    public static class update {
        @NotEmpty(message = "제목을 입력해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        @NotNull(message = "공개여부를 입력해주세요")
        private Boolean enabled;

        @Size(min = 1, message = "태그를 최소 1개를 입력해주세요")
        private List<TagDto.update> tags;

        private List<AttachImageDto.update> images;
        private List<AttachFileDto.update> files;
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
        private Long seq;
        private String title;
        private String contents;
        private Integer hitCnt;
        private String thumbnail;
        private List<TagDto.response> tags;
        private List<AttachImageDto.response> images;
        private List<AttachFileDto.response> files;
        private Integer commentCnt;
        private Long before;
        private Long next;
        private String createAt;
        private String updateAt;
        private Boolean enabled;

        public response(Blog blog, Blog before, Blog next, String uploaderDomain, FileStatus fileStatus) {
            this.seq = blog.getSeq();
            this.title = blog.getTitle();
            this.contents = blog.getContents();
            this.hitCnt = blog.getHitCnt();
            this.createAt = Formatter.dateTimeToBeautifulDate(blog.getCreateAt());
            this.updateAt = Formatter.dateTimeToBeautifulDate(blog.getUpdateAt());
            this.commentCnt = blog.getComments().size();
            this.enabled = blog.getEnabled();
            this.before = !Objects.isNull(before) ? before.getSeq() : null;
            this.next = !Objects.isNull(next) ? next.getSeq() : null;
            this.tags = new LinkedList<>();
            this.images = new LinkedList<>();
            this.files = new LinkedList<>();

            blog.getTags().forEach(tag -> this.tags.add(new TagDto.response(tag)));
            blog.getImages().forEach(image -> this.images.add(new AttachImageDto.response(image, uploaderDomain, fileStatus)));
            blog.getFiles().forEach(file -> this.files.add(new AttachFileDto.response(file, uploaderDomain, fileStatus)));

            if (!images.isEmpty()) {
                List<AttachImageSave> saves = blog.getImages().get(0).getSaves();
                Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageId().equals("origin")).findFirst();
                if (thumbnailSaveOpt.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                    this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getPath(), thumbnailSave.getFilename());
                }
            }

            //TODO temp
            //this.contents = contents.replace("http://upload.podo-dev.com/", "http://upload.podo-dev.com:8090/");

        }

    }

    @Getter
    public static class responseList {
        private Long seq;
        private String thumbnail;
        private String desc;
        private String title;
        private Integer hitCnt;
        private Integer commentCnt;
        private List<TagDto.response> tags;
        private String createAt;
        private String updateAt;
        private Boolean enabled;

        public responseList(Blog blog, String uploaderDomain) {
            this.seq = blog.getSeq();
            this.title = blog.getTitle();
            this.desc = MarkdownUtil.escape(MarkdownUtil.extractPlainText(blog.getContents()));
            this.desc = desc.length() > 300 ? desc.substring(0, 300) : desc;
            this.hitCnt = blog.getHitCnt();
            this.createAt = Formatter.dateTimeToBeautifulDate(blog.getCreateAt());
            this.updateAt = Formatter.dateTimeToBeautifulDate(blog.getUpdateAt());
            this.enabled = blog.getEnabled();
            this.commentCnt = blog.getComments().size();


            List<AttachImage> images = blog.getImages();
            if (!images.isEmpty()) {
                List<AttachImageSave> saves = blog.getImages().get(0).getSaves();
                Optional<AttachImageSave> thumbnailSaveOpt = saves.stream().filter(s -> s.getImageId().equals("origin")).findFirst();
                if (thumbnailSaveOpt.isPresent()) {
                    AttachImageSave thumbnailSave = thumbnailSaveOpt.get();
                    this.thumbnail = PathUtil.merge(uploaderDomain, thumbnailSave.getPath(), thumbnailSave.getFilename());
                }
            }

            this.tags = new LinkedList<>();
            blog.getTags().forEach(tag -> this.tags.add(new TagDto.response(tag)));
        }

        public responseList(Blog blog, String desc, String uploadServerDomain) {
            this(blog, uploadServerDomain);
            this.desc = desc;
        }

    }
}
