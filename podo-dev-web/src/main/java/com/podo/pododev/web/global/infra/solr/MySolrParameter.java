package com.podo.pododev.web.global.infra.solr;

import org.apache.solr.client.solrj.util.ClientUtils;

import java.util.HashMap;
import java.util.Map;

public class MySolrParameter {

    public static final String HIGHLIGHT_PREFIX = "((start-search))";
    public static final String HIGHLIGHT_POSTFIX = "((end-search))";

    public static Map<String, String> createSearchParam(String value, String hlFragLength) {
        final Map<String, String> param = new HashMap<>();
        param.put("q", ClientUtils.encodeLocalParamVal("(title:" + value + " OR " + "contents: " + value + ")"));
        param.put("hl", "on");
        param.put("hl.fragsize", hlFragLength);
        param.put("hl.fl", "title,contents");
        param.put("hl.simple.pre", HIGHLIGHT_PREFIX);
        param.put("hl.simple.post", HIGHLIGHT_POSTFIX);
        return param;
    }

    public static Map<String, String[]> createDefaultFacetParam(String value) {
        final Map<String, String[]> param = new HashMap<>();
        param.put("q", new String[]{""});
        param.put("facet", new String[]{"on"});
        param.put("facet.field", new String[]{"title", "contents"});
        param.put("facet.prefix", new String[]{value.toLowerCase()});
        return param;
    }


    public static Map<String, String> createDateImportParam() {
        final Map<String, String> param = new HashMap<>();

        param.put("qt", "/dataimport");
        param.put("command", "full-import");
        param.put("clean", "true");
        param.put("commit", "true");
        return param;
    }
}
