package com.podo.pododev.core.rest.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class PageDto<T> {
    Integer currentPage;
    Integer pageSize;
    Integer totalPages;
    Long totalElements;

    List<T> contents;

    @Builder
    public PageDto(Integer currentPage, Integer pageSize, Integer totalPages, Long totalElements, List<T> contents) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.contents = contents;
    }
}

