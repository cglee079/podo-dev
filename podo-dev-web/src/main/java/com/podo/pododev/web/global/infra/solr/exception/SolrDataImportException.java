package com.podo.pododev.web.global.infra.solr.exception;

public class SolrDataImportException extends RuntimeException {

    public SolrDataImportException(Exception e) {
        super("DataImport에 실패하였습니다", e);
    }
}
