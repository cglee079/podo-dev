package com.cglee079.pododev.web.global.infra.solr;

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

    public SolrSender(@Value("${infra.solr.url}") String solrUrl) {
        solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    public QueryResponse queryMap(String core, Map<String, String> param) throws SolrServerException, IOException {
        MapSolrParams queryParams = new MapSolrParams(param);
        QueryResponse response = solrClient.query(core, queryParams);
        return response;
    }

    public QueryResponse queryMultiMap(String core, Map<String, String[]> param) throws SolrServerException, IOException {
        MultiMapSolrParams queryParams = new MultiMapSolrParams(param);
        QueryResponse response = solrClient.query(core, queryParams);
        return response;
    }

}
