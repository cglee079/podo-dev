package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.domain.blog.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        private List<String> tags;

        public Blog toEntity() {
            List<Tag> tags = new LinkedList<>();

            int idx = 0;
            for (String value : this.tags) {
                tags.add(
                        Tag.builder()
                                .val(value)
                                .idx(idx++)
                                .build()
                );
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
    }


    @Getter
    public static class response {
        private Long seq;
        private String desc;
        private String title;
        private String contents;
        private Long hitCnt;
        private Date createAt;
        private Date updateAt;
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
            this.createAt = blog.getCreateAt();
            this.updateAt = blog.getUpdateAt();
            this.enabled = blog.getEnabled();
        }
    }


}
