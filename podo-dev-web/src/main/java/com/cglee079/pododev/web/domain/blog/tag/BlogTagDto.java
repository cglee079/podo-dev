package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.domain.blog.Blog;
import lombok.Getter;

public class BlogTagDto {

    @Getter
    public static class insert {
        private String val;

        public BlogTag toEntity(Blog blog) {
            return BlogTag.builder()
                    .blog(blog)
                    .val(val)
                    .build();
        }
    }

    @Getter
    public static class response {
        private Long seq;
        private String val;

        public response(BlogTag blogTag) {
            this.seq = blogTag.getSeq();
            this.val = blogTag.getVal();
        }
    }

}
