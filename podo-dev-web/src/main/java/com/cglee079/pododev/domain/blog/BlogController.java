package com.cglee079.pododev.domain.blog;

import com.cglee079.pododev.global.response.ApiResponse;
import com.cglee079.pododev.global.response.DataResponse;
import com.cglee079.pododev.global.response.ResponseStatus;
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
    public ApiResponse get(@PathVariable long seq) {
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
    public ApiResponse paging(int page) {
        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(blogService.paging(page))
                .build();
    }


    /**
     * 게시글 작성
     */
    @PostMapping
    public ApiResponse insert(@RequestBody BlogDto.insert blogReq) {
        blogService.insert(blogReq);
        return null;
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ApiResponse update(@PathVariable long seq, BlogDto.update blogReq) {
        blogService.update(seq, blogReq);

        return null;
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ApiResponse delete(@PathVariable long seq) {
        boolean result = blogService.delete(seq);

        return null;
    }


}
