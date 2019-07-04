package com.cglee079.pododev.article;

import com.cglee079.pododev.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 게시글 조회
     */
    @GetMapping("/{seq}")
    public ApiResponse get(@PathVariable long seq) {
        ArticleDto.response articleRes = articleService.get(seq);
        return ApiResponse.builder()
                .data(articleRes)
                .build();
    }

    /**
     * 게시글 페이징
     */
    @GetMapping
    public ApiResponse paging() {
        return null;
    }


    /**
     * 게시글 작성
     */
    @PostMapping
    public ApiResponse insert(ArticleDto.insert articleReq) {
        articleService.insert(articleReq);
        return null;
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ApiResponse update(@PathVariable long seq, ArticleDto.update articleReq) {
        articleService.update(seq, articleReq);

        return null;
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ApiResponse delete(@PathVariable long seq) {
        boolean result = articleService.delete(seq);

        return null;
    }


}
