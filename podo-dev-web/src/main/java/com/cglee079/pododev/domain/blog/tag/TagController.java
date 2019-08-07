package com.cglee079.pododev.domain.blog.tag;

import com.cglee079.pododev.domain.blog.Blog;
import com.cglee079.pododev.domain.blog.BlogDto;
import com.cglee079.pododev.domain.blog.BlogService;
import com.cglee079.pododev.global.response.ResponseStatus;
import com.cglee079.pododev.global.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.BitSet;
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
