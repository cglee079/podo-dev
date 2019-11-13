package com.podo.pododev.web.domain.blog.tag;

import com.podo.pododev.web.domain.blog.Blog;
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
        private Long id;
        private String val;

        public response(BlogTag blogTag) {
            this.id = blogTag.getId();
            this.val = blogTag.getVal();
        }
    }

}
