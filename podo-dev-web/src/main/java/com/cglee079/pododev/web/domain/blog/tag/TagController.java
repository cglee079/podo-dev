package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.core.global.response.ApiResponse;
import com.cglee079.pododev.core.global.response.DataResponse;
import com.cglee079.pododev.core.global.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/tags")
public class TagController {

    private final TagService tagService;

    /**
     * 게시글 조회
     */
    @GetMapping("/values")
    public ApiResponse listValues() {
        List<String> values = tagService.listValues();
        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(values)
                .build();
    }



}
