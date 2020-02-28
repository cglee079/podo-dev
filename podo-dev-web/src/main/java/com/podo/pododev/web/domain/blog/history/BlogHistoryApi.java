package com.podo.pododev.web.domain.blog.history;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.core.rest.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogHistoryApi {

    private final BlogHistoryReadService blogHistoryReadService;

    @GetMapping("/api/blogs/{blogId}/histories/{historyId}")
    public ApiResponse getHistory(@PathVariable("historyId") long historyId) {
        final BlogHistoryDto.response blogHistory = blogHistoryReadService.getById(historyId);

        return DataResponse.success()
                .result(blogHistory)
                .build();

    }
}
