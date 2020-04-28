package com.podo.pododev.web.global.config.aop.solr;

import com.podo.pododev.web.global.infra.solr.SolrDataImportService;
import com.podo.pododev.web.global.infra.solr.SolrSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class SolrDataImportAspect {

    private final SolrDataImportService solrDataImportService;

    @AfterReturning("@annotation(com.podo.pododev.web.global.config.aop.solr.SolrDataImport)")
    public void requestDataImportToSolr(JoinPoint joinPoint) {
        log.debug("AOP :: SOLR :: Solr 서버에 DataImport를 요청합니다. By '{}'", joinPoint.getSignature().getName());

        solrDataImportService.requestDataImport();
    }
}
