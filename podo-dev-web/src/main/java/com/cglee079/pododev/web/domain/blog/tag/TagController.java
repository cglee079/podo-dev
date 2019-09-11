package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class TagController {

    private final TagService tagService;

    /**
     * 태그, 초성별 조회
     */
    @GetMapping("/api/tags/values")
    public ApiResponse listValues() {
        final Map<String, Set<String>> mapByChosung = tagService.valuesByChosungMap();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(mapByChosung)
                .build();
    }


}
