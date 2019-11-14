package com.podo.pododev.web.global.infra.solr;

import com.podo.pododev.web.global.infra.solr.exception.SolrSendException;
import com.podo.pododev.core.util.MarkdownUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
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

    public List<BlogSearchVo> search(String value) {
        log.info("Solr Search, value '{}'", value);

        if (StringUtils.isEmpty(value)) {
            return Collections.emptyList();
        }

        try {
            final QueryResponse response = solrSender.requestQuerySingleValue(coreId, MySolrParameter.createSearchParam(value, hlFragLength));
            final List<SolrResponse> responseValues = response.getBeans(SolrResponse.class);
            final Map<String, Map<String, List<String>>> highlights = response.getHighlighting();

            final List<BlogSearchVo> blogSearchVos = new ArrayList<>();

            for (SolrResponse responseValue : responseValues) {
                final String blogId = responseValue.getBlogId();
                final Map<String, List<String>> hlValues = highlights.get(responseValue.getId());

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

            int schIndex = highlight.indexOf(MySolrParameter.HIGHLIGHT_PREFIX);
            if (schIndex > maxLengthBeforeHighlight) {
                highlight = highlight.substring(schIndex - maxLengthBeforeHighlight);
            }

            highlight = MarkdownUtil.escape(highlight);
            highlight = highlight.replace(MySolrParameter.HIGHLIGHT_PREFIX, "<search>");
            highlight = highlight.replace(MySolrParameter.HIGHLIGHT_POSTFIX, "</search>");

            return highlight;
        } else {
            return MarkdownUtil.extractPlainText(existContents);
        }

    }

    public List<String> getFacets(String value) {
        log.info("Solr facets, value '{}'", value);

        final Map<String, String[]> param = MySolrParameter.createFacetParam(value);

        try {
            final QueryResponse response = solrSender.requestQueryMultiValue(coreId, param);

            final List<FacetField.Count> titleFacet = response.getFacetFields().get(0).getValues(); // TITLE
            final List<FacetField.Count> contentFacet = response.getFacetFields().get(1).getValues(); // CONTENTS

            final Set<String> result = new HashSet<>();
            result.addAll(titleFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList()));
            result.addAll(contentFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList()));

            return result.stream()
                    .sorted()
                    .collect(Collectors.toList());

        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }

    }

    public void dataimport() {
        final Map<String, String> param = MySolrParameter.createDateImportParam();

        try {
            solrSender.requestQuerySingleValue(coreId, param);
        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }
    }


}

