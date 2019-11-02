package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogTagController {

    private final BlogTagService blogTagService;

    /**
     * 태그, 초성별 조회
     */
    @GetMapping("/api/tags")
    public ApiResponse listValues() {
        final List<String> values = blogTagService.getAll();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(values)
                .build();
    }


}
