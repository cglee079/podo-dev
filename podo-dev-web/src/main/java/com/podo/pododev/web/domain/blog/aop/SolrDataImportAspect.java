package com.podo.pododev.web.domain.blog.aop;

import com.podo.pododev.web.global.infra.solr.MySolrClient;
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

    private final MySolrClient mySolrClient;

    @AfterReturning("@annotation(com.podo.pododev.web.domain.blog.aop.SolrDataImport)")
    public void doDataimport(JoinPoint joinPoint) {
        log.info("AOP, Solr Data Import By '{}'", joinPoint.getSignature().getName());

        mySolrClient.dataimport();
    }
}
