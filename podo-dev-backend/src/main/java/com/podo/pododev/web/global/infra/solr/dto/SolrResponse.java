package com.podo.pododev.web.global.infra.solr.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

@Getter
public class SolrResponse {

    @Field("id")
    private String id;

    @Field("blog_id")
    private String blogId;

    @Field("title")
    private String title;

    @Field("contents")
    private String contents;
}
