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

        Map<Integer, List<BlogDto.archive>> archive = blogReadService.getArchiveMapByYearOfPublishAt();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(archive)
                .build();
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/api/blogs/{blogId}")
    public ApiResponse get(@PathVariable Long blogId) {

        final BlogDto.response blog = blogReadService.getByBlogId(blogId);

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
        final PageDto<BlogDto.responseGroup> blogs = blogReadService.paging(request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blogs)
                .build();
    }

    @GetMapping("/api/blogs/facets")
    public ApiResponse facets(@RequestParam String searchValue) {
        final List<String> facets = blogReadService.getIndexedWordByKeyword(searchValue);

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(facets)
                .build();
    }


    /**
     * 게시글 작성
     */
    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogWriteService.insertNewBlog(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/api/blogs/{blogId}")
    public ApiResponse update(@PathVariable Long id, @Valid @RequestBody BlogDto.update blogReq) {
        blogWriteService.updateExistedBlogs(id, blogReq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/blogs/{blogId}")
    public ApiResponse delete(@PathVariable Long id) {
        blogWriteService.removeByBlogId(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 조회
     */
    @PostMapping("/api/blogs/{blogId}/hitCount")
    public ApiResponse increaseHitCount(@PathVariable Long id) {

        blogWriteService.increaseHitCount(id);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
