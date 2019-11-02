package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.*;
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
public class BlogController {

    private final BlogService blogService;

    /**
     * 게시글 조회
     */
    @GetMapping("/api/blogs/archive")
    public ApiResponse getArchive() {

        Map<Integer, List<BlogDto.archive>> archive = blogService.getArchive();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(archive)
                .build();
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/api/blogs/{id}")
    public ApiResponse get(@PathVariable Long id) {

        BlogDto.response blogRes = blogService.get(id);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(blogRes)
                .build();
    }

    /**
     * 게시글 페이징
     */
    @GetMapping("/api/blogs")
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
    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogService.insert(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/api/blogs/{id}")
    public ApiResponse update(@PathVariable Long id, @Valid @RequestBody BlogDto.update blogReq) {
        blogService.update(id, blogReq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/blogs/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        blogService.delete(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 조회
     */
    @PostMapping("/api/blogs/{id}/hitCount")
    public ApiResponse increaseHitCnt(@PathVariable Long id) {

        blogService.increaseHitCnt(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    @GetMapping("/api/blogs/facets")
    public ApiResponse facets(@RequestParam String value) {
        final List<String> facets = blogService.facets(value);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(facets)
                .build();
    }

}
