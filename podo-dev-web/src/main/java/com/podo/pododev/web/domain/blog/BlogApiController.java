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
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogApiController {

    private final BlogWriteService blogWriteService;
    private final BlogReadService blogReadService;

    @GetMapping("/api/blogs/archive")
    public ApiResponse getArchive() {

        final Map<Integer, List<BlogDto.archive>> archive = blogReadService.getArchiveMapByYearOfPublishAt();

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(archive)
                .build();
    }

    @GetMapping("/api/blogs/{blogId}")
    public ApiResponse get(@PathVariable Long blogId) {

        final BlogDto.response blog = blogReadService.getByBlogId(blogId);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blog)
                .build();
    }

    @GetMapping("/api/blogs")
    public ApiResponse paging(BlogDto.request request) {
        final PageDto<BlogDto.responseGroup> blogs = blogReadService.paging(request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blogs)
                .build();
    }

    @GetMapping("/api/blogs/words")
    public ApiResponse facets(@RequestParam String searchValue) {
        final Set<String> facets = blogReadService.getIndexedWordByKeyword(searchValue);

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(facets)
                .build();
    }


    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogWriteService.insertNewBlog(insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    @PatchMapping("/api/blogs/{blogId}")
    public ApiResponse update(@PathVariable Long blogId, @Valid @RequestBody BlogDto.update blogReq) {
        blogWriteService.updateExistedBlogs(blogId, blogReq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    @DeleteMapping("/api/blogs/{blogId}")
    public ApiResponse delete(@PathVariable Long blogId) {
        blogWriteService.removeByBlogId(blogId);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


    @PostMapping("/api/blogs/{blogId}/hitCount")
    public ApiResponse increaseHitCount(@PathVariable Long blogId) {

        blogWriteService.increaseHitCount(blogId);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
