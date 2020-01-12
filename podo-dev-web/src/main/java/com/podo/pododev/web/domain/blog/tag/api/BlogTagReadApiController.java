package com.podo.pododev.web.domain.blog.tag.api;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.CollectionResponse;
import com.podo.pododev.web.domain.blog.tag.service.BlogTagReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogTagReadApiController {

    private final BlogTagReadService blogTagReadService;

    @GetMapping("/api/tags")
    public ApiResponse getAllDistinctTagValues() {
        final List<String> distinctTagValues = blogTagReadService.getAllDistinctTagValues(true);

        return CollectionResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(distinctTagValues)
                .build();
    }


}
