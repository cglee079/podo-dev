package com.podo.pododev.web.domain.blog.api;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.BlogDto;
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
public class BlogWriteApi {

    private final BlogWriteService blogWriteService;


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
