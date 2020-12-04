package com.podo.pododev.web.domain.blog.history.application;

import com.podo.pododev.web.domain.blog.history.dto.BlogHistoryResponse;
import com.podo.pododev.web.domain.blog.history.exception.InvalidBlogHistoryIdApiException;
import com.podo.pododev.web.domain.blog.history.model.BlogHistory;
import com.podo.pododev.web.domain.blog.history.repository.BlogHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogHistoryReadService {

    private final BlogHistoryRepository blogHistoryRepository;

    public BlogHistoryResponse getById(long historyId) {
        final Optional<BlogHistory> blogHistoryOptional = blogHistoryRepository.findById(historyId);

        final BlogHistory blogHistory = blogHistoryOptional.orElseThrow(() -> new InvalidBlogHistoryIdApiException(historyId));

        return new BlogHistoryResponse(blogHistory);
    }
}
