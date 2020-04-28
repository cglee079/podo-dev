package com.podo.pododev.web.global.infra.solr;

import com.podo.pododev.web.global.infra.solr.client.SolrSender;
import com.podo.pododev.web.global.infra.solr.exception.SolrDataImportException;
import com.podo.pododev.web.global.infra.solr.helper.SolrParameterCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SolrDataImportService {

    @Value("${infra.solr.core_id}")
    private String solrCoreId;

    private final SolrSender solrSender;

    public void requestDataImport() {
        final Map<String, String> param = SolrParameterCreator.createDateImportParam();

        try {
            solrSender.requestWithSingleValueParam(solrCoreId, param);
        } catch (SolrServerException | IOException e) {
            throw new SolrDataImportException(e);
        }
    }


}

