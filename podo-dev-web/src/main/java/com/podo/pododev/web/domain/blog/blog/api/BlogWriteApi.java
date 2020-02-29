package com.podo.pododev.web.domain.blog.blog.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.application.BlogInsertService;
import com.podo.pododev.web.domain.blog.blog.application.BlogRemoveService;
import com.podo.pododev.web.domain.blog.blog.application.BlogUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogWriteApi {

    private final BlogInsertService blogInsertService;
    private final BlogUpdateService blogUpdateService;
    private final BlogRemoveService blogRemoveService;

    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogInsertService.insertNewBlog(insert);
        return StatusResponse.success();
    }

    @PatchMapping("/api/blogs/{blogId}")
    public ApiResponse update(@PathVariable Long blogId, @Valid @RequestBody BlogDto.update blogReq) {
        blogUpdateService.updateExistedBlogs(blogId, blogReq);
        return StatusResponse.success();
    }

    @DeleteMapping("/api/blogs/{blogId}")
    public ApiResponse delete(@PathVariable Long blogId) {
        blogRemoveService.removeByBlogId(blogId);
        return StatusResponse.success();
    }

    @PostMapping("/api/blogs/{blogId}/hitCount")
    public ApiResponse increaseHitCount(@PathVariable Long blogId) {
        blogUpdateService.increaseHitCount(blogId);
        return StatusResponse.success();
    }


}
