package com.cglee079.pododev.web.global.infra.solr;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

@Setter
@Getter
public class SolrResponse {

    @Field("id")
    private String id;

    @Field("seq")
    private String seq;

    @Field("title")
    private String title;

    @Field("contents")
    private String contents;
}
