package com.cglee079.pododev.web.domain.blog.tag;

import lombok.Getter;

public class TagDto {

    @Getter
    public static class insert {
        private String val;

        public Tag toEntity() {
            return Tag.builder()
                    .val(val)
                    .build();
        }
    }


    @Getter
    public static class update {
        private Long seq;
        private String val;

        public Tag toEntity(Long blogSeq) {
            return Tag.builder()
                    .blogSeq(blogSeq)
                    .val(val)
                    .build();
        }
    }

    @Getter
    public static class response {
        private Long seq;
        private String val;

        public response(Tag tag) {
            this.seq = tag.getSeq();
            this.val = tag.getVal();
        }
    }

}
