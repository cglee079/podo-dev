package com.cglee079.pododev.domain.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;

public class BlogDto {

    @Getter
    public static class insert {
        private String title;
        private String contents;

        public Blog toEntity(){
            return Blog.builder()
                    .title(title)
                    .contents(contents)
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
        private long seq;
        private String desc;
        private String title;
        private String contents;
        private long hitCnt;
        private Date createAt;
        private Date updateAt;
        private boolean enabled;

        public response(Blog blog){
            String desc = "";
            Document doc= Jsoup.parse(blog.getContents());
            Elements els= doc.select("*");

            if(els.eachText().size() > 0){
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
            this.enabled = blog.isEnabled();
        }
    }


}
