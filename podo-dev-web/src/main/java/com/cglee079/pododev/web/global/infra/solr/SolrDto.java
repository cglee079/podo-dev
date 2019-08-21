package com.cglee079.pododev.web.global.infra.solr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

public class SolrDto {

    @Setter
    @Getter
    public static class response {
        @Field("id")
        private String id;

        @Field("seq")
        private String seq;

        @Field("title")
        private String title;

        @Field("contents")
        private String contents;
    }

}
