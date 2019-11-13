package com.podo.pododev.web.domain.blog;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.service.BlogReadService;
import com.podo.pododev.web.domain.blog.service.BlogWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogApiController {

    private final BlogWriteService blogWriteService;
    private final BlogReadService blogReadService;

    /**
     * 게시글 조회
     */
    @GetMapping("/api/blogs/archive")
    public ApiResponse getArchive() {

        Map<Integer, List<BlogDto.archive>> archive = blogReadService.getArchive();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(archive)
                .build();
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/api/blogs/{id}")
    public ApiResponse get(@PathVariable Long id) {

        BlogDto.response blog = blogReadService.get(id);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blog)
                .build();
    }

    /**
     * 게시글 페이징
     */
    @GetMapping("/api/blogs")
    public ApiResponse paging(BlogDto.request request) {
        final PageDto<BlogDto.responseList> blogs = blogReadService.paging(request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blogs)
                .build();
    }

    @GetMapping("/api/blogs/facets")
    public ApiResponse facets(@RequestParam String value) {
        final List<String> facets = blogReadService.facets(value);

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .results(facets)
                .build();
    }


    /**
     * 게시글 작성
     */
    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogWriteService.insert(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/api/blogs/{id}")
    public ApiResponse update(@PathVariable Long id, @Valid @RequestBody BlogDto.update blogReq) {
        blogWriteService.update(id, blogReq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/blogs/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        blogWriteService.delete(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 조회
     */
    @PostMapping("/api/blogs/{id}/hitCount")
    public ApiResponse increaseHitCnt(@PathVariable Long id) {

        blogWriteService.increaseHitCnt(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
