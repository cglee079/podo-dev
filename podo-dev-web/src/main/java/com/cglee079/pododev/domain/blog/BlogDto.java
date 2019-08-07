package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.domain.blog.comment.CommentDto;
import com.cglee079.pododev.domain.blog.tag.Tag;
import com.cglee079.pododev.domain.blog.tag.TagDto;
import com.cglee079.pododev.global.util.Formatter;
import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BlogDto {

    @Getter
    public static class insert {
        private String title;
        private String contents;
        private Boolean enabled;
        private List<TagDto.insert> tags;


        public Blog toEntity() {
            List<Tag> tags = new LinkedList<>();


            this.tags.forEach(tag -> tags.add(tag.toEntity()));

            int idx = 1;
            for (Tag tag : tags) {
                tag.updateIdx(idx++);
            }

            return Blog.builder()
                    .title(title)
                    .contents(contents)
                    .enabled(enabled)
                    .tags(tags)
                    .build();
        }
    }

    @Getter
    public static class update {
        private String title;
        private String contents;
        private Boolean enabled;
        private List<TagDto.update> tags;

        public Blog toEntity() {

            List<Tag> tags = new LinkedList<>();
            this.tags.forEach(t -> tags.add(t.toEntity()));

            return Blog.builder()
                    .title(title)
                    .contents(contents)
                    .enabled(enabled)
                    .tags(tags)
                    .build();
        }
    }

    @Setter
    @Getter
    public class request {
        Integer page;
    }


    @Getter
    public static class response {
        private Long seq;
        private String desc;
        private String title;
        private String contents;
        private Integer hitCnt;
        private List<TagDto.response> tags;
        private String createAt;
        private String updateAt;
        private Boolean enabled;

        public response(Blog blog) {
            String desc = "";
            Document doc = Jsoup.parse(blog.getContents());
            Elements els = doc.select("*");

            if (els.eachText().size() > 0) {
                desc = els.eachText().get(0);
            }
            desc.replace("\n", " ");

            this.seq = blog.getSeq();
            this.title = blog.getTitle();
            this.desc = desc;
            this.contents = blog.getContents();
            this.hitCnt = blog.getHitCnt();
            this.createAt = Formatter.dateTimeToStr(blog.getCreateAt());
            this.updateAt = Formatter.dateTimeToStr(blog.getUpdateAt());
            this.enabled = blog.getEnabled();
            this.tags = new LinkedList<>();

            blog.getTags().forEach(tag -> this.tags.add(new TagDto.response(tag)));
        }
    }



}
