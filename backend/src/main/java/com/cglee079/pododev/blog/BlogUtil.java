package com.cglee079.pododev.blog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BlogUtil {

    public static Blog insertToEntity(BlogDto.insert insert) {
        return Blog.builder()
                .title(insert.getTitle())
                .contents(insert.getContents())
                .build();
    }

    public static BlogDto.response entityToResponse(Blog blog) {

        String desc = "";
        Document doc= Jsoup.parse(blog.getContents());
        Elements els= doc.select("*");

        if(els.eachText().size() > 0){
            desc = els.eachText().get(0);
        }
        desc.replace("\n", " ");

        return BlogDto.response.builder()
                .seq(blog.getSeq())
                .title(blog.getTitle())
                .desc(desc)
                .contents(blog.getContents())
                .hitCnt(blog.getHitCnt())
                .createAt(blog.getCreateAt())
                .updateAt(blog.getUpdateAt())
                .build();
    }
}
