package com.podo.pododev.web.domain.blog.blog.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.application.BlogWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogWriteApi {

    private final BlogWriteService blogWriteService;


    @PostMapping("/api/blogs")
    public ApiResponse insert(@Valid @RequestBody BlogDto.insert insert) {
        blogWriteService.insertNewBlog(insert);

        return StatusResponse.success();
    }


    @PatchMapping("/api/blogs/{blogId}")
    public ApiResponse update(@PathVariable Long blogId, @Valid @RequestBody BlogDto.update blogReq) {
        blogWriteService.updateExistedBlogs(blogId, blogReq);

        return StatusResponse.success();
    }

    @DeleteMapping("/api/blogs/{blogId}")
    public ApiResponse delete(@PathVariable Long blogId) {
        blogWriteService.removeByBlogId(blogId);

        return StatusResponse.success();
    }


    @PostMapping("/api/blogs/{blogId}/hitCount")
    public ApiResponse increaseHitCount(@PathVariable Long blogId) {

        blogWriteService.increaseHitCount(blogId);

        return StatusResponse.success();
    }

}
