package com.cglee079.pododev.web.domain.blog;

import com.cglee079.pododev.core.global.response.ResponseStatus;
import com.cglee079.pododev.core.global.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
                .status(ResponseStatus.SUCCESS)
                .data(blogRes)
                .build();
    }

    /**
     * 게시글 페이징
     */
    @GetMapping
    public ApiResponse paging(BlogDto.request request) {
        PageDto<BlogDto.responseList> blogs = blogService.paging(request);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
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
                .status(ResponseStatus.SUCCESS)
                .build();
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ApiResponse update(@PathVariable Long seq, @RequestBody BlogDto.update blogReq) {
        blogService.update(seq, blogReq);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ApiResponse delete(@PathVariable Long seq) {
        blogService.delete(seq);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }


}
