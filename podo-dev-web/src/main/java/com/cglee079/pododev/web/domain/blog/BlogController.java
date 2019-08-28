package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/blogs")
public class BlogController {

    private final BlogService blogService;

    /**
     * 게시글 조회
     */
    @GetMapping("/{seq}")
    public ApiResponse get(@PathVariable Long seq) {
        BlogDto.response blogRes = blogService.get(seq);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(blogRes)
                .build();
    }

    /**
     * 게시글 페이징
     */
    @GetMapping
    public ApiResponse paging(BlogDto.request request) {
        final PageDto<BlogDto.responseList> blogs = blogService.paging(request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(blogs)
                .build();
    }


    /**
     * 게시글 작성
     */
    @PostMapping
    public ApiResponse insert(@RequestBody BlogDto.insert insert) {
        blogService.insert(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ApiResponse update(@PathVariable Long seq, @RequestBody BlogDto.update blogReq) {
        blogService.update(seq, blogReq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ApiResponse delete(@PathVariable Long seq) {
        blogService.delete(seq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    @GetMapping("/facets")
    public ApiResponse facets(@RequestParam String value) {
        final List<String> facets = blogService.facets(value);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(facets)
                .build();
    }

}
