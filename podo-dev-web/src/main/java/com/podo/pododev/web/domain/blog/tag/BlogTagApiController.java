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

    /**
     * 태그, 초성별 조회
     */
    @GetMapping("/api/tags")
    public ApiResponse getAllDistinctTagValue() {
        final List<String> values = blogTagService.getAllDistinctTagValue();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .results(values)
                .build();
    }


}
