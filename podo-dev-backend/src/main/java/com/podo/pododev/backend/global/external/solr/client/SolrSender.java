package com.podo.pododev.backend.global.external.solr.client;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.MultiMapSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SolrSender {

    private final SolrClient solrClient;

    public SolrSender(@Value("${external.solr.url:}") String solrUrl) {
        solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    public QueryResponse requestWithSingleValueParam(String core, Map<String, String> param) throws SolrServerException, IOException {
        MapSolrParams queryParams = new MapSolrParams(param);
        return solrClient.query(core, queryParams);
    }

    public QueryResponse requestWithMultiValueParam(String core, Map<String, String[]> param) throws SolrServerException, IOException {
        MultiMapSolrParams queryParams = new MultiMapSolrParams(param);
        return solrClient.query(core, queryParams);
    }

}
