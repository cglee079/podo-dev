package com.podo.pododev.web.domain.blog.tag.service;

import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BlogTagReadService {

    private final BlogTagRepository blogTagRepository;

    public List<String> getAllDistinctTagValues(boolean enabled) {
        return blogTagRepository.findDistinctTagValue(enabled);
    }
}
