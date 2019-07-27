package com.cglee079.pododev.domain.api;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PageDto {

    @Getter
    @Builder
    public static class response<T> {
        int currentPage;
        int pageSize;
        int totalPages;
        long totalElements;
        List<T> contents;
    }

}
