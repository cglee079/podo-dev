package com.cglee079.pododev.web.global.infra.solr;

import com.cglee079.pododev.web.global.util.MarkdownUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MySolrClient {

    private final SolrSender solrSender;

    @Value("${infra.solr.core.id}")
    private String core;
    @Value("${infra.solr.query.hl.max.pre.length}")
    private int maxHlPreLength;
    @Value("${infra.solr.query.hl.frag.length}")
    private String hlFragLength;
    @Value("${infra.solr.query.hl.simple.pre}")
    private String hlSimplePre;
    @Value("${infra.solr.query.hl.simple.post}")
    private String hlSimplePost;

    public List<SolrDto.response> search(String value) {
        log.info("Solr Search, value '{}'", value);

        if (StringUtils.isEmpty(value)) {
            return new LinkedList<>();
        }

        String q = "(title:" + value + " OR " + "contents: " + value + ")";
        Map<String, String> param = new HashMap<>();

        param.put("q", ClientUtils.encodeLocalParamVal(q));
        param.put("hl", "on");
        param.put("hl.fragsize", hlFragLength);
        param.put("hl.fl", "title,contents");
        param.put("hl.simple.pre", hlSimplePre);
        param.put("hl.simple.post", hlSimplePost);

        try {
            QueryResponse response = solrSender.query(core, param);
            List<SolrDto.response> results = response.getBeans(SolrDto.response.class);

            Map<String, Map<String, List<String>>> hls = response.getHighlighting();

            //Set Highlight;
            results.forEach(result -> {
                Map<String, List<String>> hl = hls.get(result.getId());

//                //title에서 분석됬는지 확인
//                if (hl.containsKey("title")) {
//                    result.setTitle(hl.get("title").get(0));
//                }

                //Contents에서 분석됬는지 확인.
                if (hl.containsKey("contents")) {
                    String highlight = MarkdownUtil.extractPlainText(hl.get("contents").get(0));

                    int schIndex = highlight.indexOf(hlSimplePre);
                    if (schIndex > maxHlPreLength) {
                        highlight = highlight.substring(schIndex - maxHlPreLength);
                    }

                    highlight = highlight.replace(hlSimplePre, "<search>");
                    highlight = highlight.replace(hlSimplePost, "</search>");

                    result.setContents(highlight);
                } else {
                    result.setContents(MarkdownUtil.extractPlainText(result.getContents()));
                }


            });


            return results;

        } catch (SolrServerException | IOException e) {
            //TODO
            e.printStackTrace();
            return new LinkedList<>();
        }


    }

    public List<String> getFacets(String value) {
        log.info("Solr facets, value '{}'", value);

        Map<String, String> param = new HashMap<>();
        param.put("q", "");
        param.put("facet", "on");
        param.put("facet.field", "contents");
        param.put("facet.prefix", value.toLowerCase());

        try {
            QueryResponse response = solrSender.query(core, param);
            List<FacetField.Count> counts = response.getFacetFields().get(0).getValues(); // CONTENTS

            return counts.stream().map(FacetField.Count::getName).collect(Collectors.toList());
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }

    }

    public void dataimport() throws SolrServerException, IOException {
        Map<String, String> param = new HashMap<String, String>();

        param.put("qt", "/dataimport");
        param.put("command", "full-import");
        param.put("clean", "true");
        param.put("commit", "true");

        solrSender.query(core, param);
    }

}

