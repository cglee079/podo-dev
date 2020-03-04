package com.podo.pododev.web.domain.blog.history;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogHistoryApi {

    private final BlogHistoryReadService blogHistoryReadService;

    @GetMapping("/api/blogs/{blogId}/histories/{historyId}")
    public BlogHistoryDto.response getHistory(@PathVariable("historyId") long historyId) {
        return blogHistoryReadService.getById(historyId);
    }
}
