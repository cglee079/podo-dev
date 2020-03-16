package com.podo.pododev.web.domain.blog.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogHistoryReadService {

    private final BlogHistoryRepository blogHistoryRepository;

    public BlogHistoryDto.response getById(long historyId) {
        final Optional<BlogHistory> blogHistoryOptional = blogHistoryRepository.findById(historyId);

        final BlogHistory blogHistory = blogHistoryOptional.orElseThrow(() -> new InvalidBlogHistoryIdApiException(historyId));

        return new BlogHistoryDto.response(blogHistory);
    }
}
