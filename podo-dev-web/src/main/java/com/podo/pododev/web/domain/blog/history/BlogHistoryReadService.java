package com.podo.pododev.web.domain.blog.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogHistoryReadService {

    private final BlogHistoryRepository blogHistoryRepository;

    public BlogHistoryDto.response getById(long historyId) {
        final Optional<BlogHistory> blogHistoryOptional = blogHistoryRepository.findById(historyId);

        final BlogHistory blogHistory = blogHistoryOptional.orElseThrow(() -> new InvalidBlogHistoryIdApiException(historyId));

        return new BlogHistoryDto.response(blogHistory);
    }
}
