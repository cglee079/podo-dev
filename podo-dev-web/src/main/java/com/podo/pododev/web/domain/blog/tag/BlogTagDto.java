package com.podo.pododev.web.domain.blog.tag;

import com.podo.pododev.web.domain.blog.blog.Blog;
import lombok.Getter;

public class BlogTagDto {

    @Getter
    public static class insert {
        private String tagValue;

        public BlogTag toEntity(Blog blog) {
            return new BlogTag(blog, tagValue);
        }
    }

    @Getter
    public static class response {
        private Long id;
        private String tagValue;

        public response(BlogTag blogTag) {
            this.id = blogTag.getId();
            this.tagValue = blogTag.getTagValue();
        }
    }

}
