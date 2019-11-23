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

    @Value("${infra.solr.core_id}")
    private String solrCoreId;

    @Value("${infra.solr.highlight.pre_max_text_length}")
    private Integer maxTextLengthBeforeHighlight;

    @Value("${infra.solr.highlight.frag.length}")
    private String highlightFragLength;

    private final SolrSender solrSender;

    public List<BlogSearchResultVo> search(String searchValue) {
        log.info("Solr 엔진 '{}' 검색", searchValue);

        if (StringUtils.isEmpty(searchValue)) {
            return Collections.emptyList();
        }

        try {
            final QueryResponse response = solrSender.requestWithSingleValueParam(solrCoreId, MySolrParameter.createSearchParam(searchValue, highlightFragLength));
            final List<SolrResponse> responseValues = response.getBeans(SolrResponse.class);
            final Map<String, Map<String, List<String>>> highlights = response.getHighlighting();

            final List<BlogSearchResultVo> blogSearchResultVos = new ArrayList<>();

            for (SolrResponse responseValue : responseValues) {
                final String blogId = responseValue.getBlogId();
                final Map<String, List<String>> hlValues = highlights.get(responseValue.getId());

                final String contents = getHighlightContents(hlValues, responseValue.getContents());
                final String title = getHighlightBlogTitle(hlValues, responseValue.getTitle());

                blogSearchResultVos.add(new BlogSearchResultVo(blogId, title, contents));
            }

            return blogSearchResultVos;

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

            String highlightContents = MarkdownUtil.extractPlainText(hlValues.get("contents").get(0));

            int indexOfHighlightPrefix = highlightContents.indexOf(MySolrParameter.HIGHLIGHT_PREFIX);
            if (indexOfHighlightPrefix > maxTextLengthBeforeHighlight) {
                highlightContents = highlightContents.substring(indexOfHighlightPrefix - maxTextLengthBeforeHighlight);
            }

            highlightContents = MarkdownUtil.escapeHtml(highlightContents);
            highlightContents = highlightContents.replace(MySolrParameter.HIGHLIGHT_PREFIX, "<search>");
            highlightContents = highlightContents.replace(MySolrParameter.HIGHLIGHT_POSTFIX, "</search>");

            return highlightContents;
        } else {
            return MarkdownUtil.extractPlainText(existContents);
        }

    }

    public Set<String> getIndexedWordsByKeyword(String keyword) {
        log.info("Solr 엔진, '{}' 관련 색인 단어 조회", keyword);

        final Map<String, String[]> solrFacetRequestParam = MySolrParameter.createDefaultFacetParam(keyword);

        try {
            final QueryResponse queryResponse = solrSender.requestWithMultiValueParam(solrCoreId, solrFacetRequestParam);

            final List<FacetField.Count> titleFacet = queryResponse.getFacetFields().get(0).getValues();
            final List<FacetField.Count> contentFacet = queryResponse.getFacetFields().get(1).getValues();

            final Set<String> result = new HashSet<>();
            result.addAll(titleFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList()));
            result.addAll(contentFacet.stream().map(FacetField.Count::getName).collect(Collectors.toList()));

            return result.stream()
                    .sorted()
                    .collect(Collectors.toSet());

        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }

    }

    public void dataimport() {
        final Map<String, String> param = MySolrParameter.createDateImportParam();

        try {
            solrSender.requestWithSingleValueParam(solrCoreId, param);
        } catch (SolrServerException | IOException e) {
            throw new SolrSendException(e);
        }
    }


}

