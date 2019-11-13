package com.podo.pododev.web.global.infra.solr;

import com.podo.pododev.web.global.infra.solr.exception.SolrSendException;
import com.podo.pododev.core.util.MarkdownUtil;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MySolrClient {

    private final SolrSender solrSender;

    @Value("${infra.solr.core.id}")
    private String coreId;

    @Value("${infra.solr.query.hl.pre.max.length}")
    private Integer maxLengthBeforeHighlight;

    @Value("${infra.solr.query.hl.frag.length}")
    private String hlFragLength;

    @Value("${infra.solr.query.hl.simple.pre}")
    private String hlPrefix;

    @Value("${infra.solr.query.hl.simple.post}")
    private String hlPostfix;

    public List<BlogSearchVo> search(String value) {
        log.info("Solr Search, value '{}'", value);

        if (StringUtils.isEmpty(value)) {
            return Collections.emptyList();
        }

        final Map<String, String> param = createSearchParam(value);

        try {
            final QueryResponse response = solrSender.queryMap(coreId, param);
            final List<SolrResponse> responseValues = response.getBeans(SolrResponse.class);
            final Map<String, Map<String, List<String>>> highlights = response.getHighlighting();

            final List<BlogSearchVo> blogSearchVos = new ArrayList<>();

            //Set Highlight
            for (SolrResponse responseValue : responseValues) {
                final String blogId = responseValue.getBlogId();

                Map<String, List<String>> hlValues = highlights.get(responseValue.getId());

                final String contents = getHighlightContents(hlValues, responseValue.getContents());
                final String title = getHighlightBlogTitle(hlValues, responseValue.getTitle());

                blogSearchVos.add(new BlogSearchVo(blogId, title, contents));
            }

            return blogSearchVos;

        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }


    }

    private String getHighlightBlogTitle(Map<String, List<String>> hlValues, String existTitle) {
        if (hlValues.containsKey("title")) {
            return hlValues.get("title").get(0);
        }
        return existTitle;
    }

    private String getHighlightContents(Map<String, List<String>> hlValues, String existContents) {
        if (hlValues.containsKey("contents")) {

            String highlight = MarkdownUtil.extractPlainText(hlValues.get("contents").get(0));

            int schIndex = highlight.indexOf(hlPrefix);
            if (schIndex > maxLengthBeforeHighlight) {
                highlight = highlight.substring(schIndex - maxLengthBeforeHighlight);
            }

            highlight = MarkdownUtil.escape(highlight);
            highlight = highlight.replace(hlPrefix, "<search>");
            highlight = highlight.replace(hlPostfix, "</search>");

            return highlight;
        } else {
            return MarkdownUtil.extractPlainText(existContents);
        }

    }

    public List<String> getFacets(String value) {
        log.info("Solr facets, value '{}'", value);

        final Map<String, String[]> param = createFacetParam(value);

        try {
            QueryResponse response = solrSender.queryMultiMap(coreId, param);

            List<FacetField.Count> titleFacet = response.getFacetFields().get(0).getValues(); // TITLE
            List<FacetField.Count> contentFacet = response.getFacetFields().get(1).getValues(); // CONTENTS

            List<String> value1 = titleFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList());
            List<String> value2 = contentFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList());

            Set<String> result = new HashSet<>();
            result.addAll(value1);
            result.addAll(value2);

            return result.stream()
                    .sorted()
                    .collect(Collectors.toList());

        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }

    }

    public void dataimport() {
        final Map<String, String> param = createDateImportParam();

        try {
            solrSender.queryMap(coreId, param);
        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }
    }

    private Map<String, String> createSearchParam(String value) {
        final Map<String, String> param = new HashMap<>();
        param.put("q", ClientUtils.encodeLocalParamVal("(title:" + value + " OR " + "contents: " + value + ")"));
        param.put("hl", "on");
        param.put("hl.fragsize", hlFragLength);
        param.put("hl.fl", "title,contents");
        param.put("hl.simple.pre", hlPrefix);
        param.put("hl.simple.post", hlPostfix);
        return param;
    }

    private Map<String, String[]> createFacetParam(String value) {
        final Map<String, String[]> param = new HashMap<>();
        param.put("q", new String[]{""});
        param.put("facet", new String[]{"on"});
        param.put("facet.field", new String[]{"title", "contents"});
        param.put("facet.prefix", new String[]{value.toLowerCase()});
        return param;
    }


    private Map<String, String> createDateImportParam() {
        final Map<String, String> param = new HashMap<>();

        param.put("qt", "/dataimport");
        param.put("command", "full-import");
        param.put("clean", "true");
        param.put("commit", "true");
        return param;
    }

}

