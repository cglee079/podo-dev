package com.podo.pododev.backend.domain.blog.tag.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.CollectionResponse;
import com.podo.pododev.backend.domain.blog.tag.application.BlogTagReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogTagReadApi {

    private final BlogTagReadService blogTagReadService;

    @GetMapping("/api/tags")
    public ApiResponse getAllDistinctTagValues() {
        final List<String> distinctTagValues = blogTagReadService.getAllDistinctTagValues(true);

        return CollectionResponse.ok(distinctTagValues);
    }


}
