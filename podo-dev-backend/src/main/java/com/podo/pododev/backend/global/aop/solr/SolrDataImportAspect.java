package com.podo.pododev.backend.global.aop.solr;

import com.podo.pododev.backend.global.context.ThreadLocalContext;
import com.podo.pododev.backend.global.external.solr.SolrDataImportService;
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

    @AfterReturning("@annotation(com.podo.pododev.backend.global.aop.solr.SolrDataImport)")
    public void requestDataImportToSolr(JoinPoint joinPoint) {
        ThreadLocalContext.debug(String.format("SOLR :: Solr 서버에 DataImport를 요청합니다. By '%s'", joinPoint.getSignature().getName()));

        solrDataImportService.requestDataImport();
    }
}