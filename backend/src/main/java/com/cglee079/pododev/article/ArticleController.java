package com.cglee079.pododev.article;

import com.cglee079.pododev.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.Date;

@RestController("/api/articles")
public class ArticleController {

    /**
     * 게시글 조회
     */
    @GetMapping("/{seq}")
    public ApiResponse get(@PathVariable long seq) {
        return null;
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
    public ApiResponse upload() {
        return null;
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ApiResponse update(@PathVariable long seq) {
        return null;
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ApiResponse delete(@PathVariable long seq) {
        return null;
    }


}
