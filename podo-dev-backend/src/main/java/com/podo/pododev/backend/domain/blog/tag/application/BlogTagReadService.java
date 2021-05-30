package com.podo.pododev.backend.domain.blog.tag.application;

import com.podo.pododev.backend.domain.blog.tag.repository.BlogTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BlogTagReadService {

    private final BlogTagRepository blogTagRepository;

    public List<String> getAllDistinctTagValues(boolean blogEnabled) {
        return blogTagRepository.findDistinctTagValue(blogEnabled);
    }
}
