package com.podo.pododev.web.domain.blog.tag;

import com.podo.pododev.core.rest.response.ApiResponse;
import com.podo.pododev.core.rest.response.ApiStatus;
import com.podo.pododev.core.rest.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogTagApiController {

    private final BlogTagService blogTagService;

    @GetMapping("/api/tags")
    public ApiResponse getAllDistinctTagValues() {
        final List<String> distinctTagValues = blogTagService.getAllDistinctTagValuesByBlogEnabledTrue();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(distinctTagValues)
                .build();
    }


}
