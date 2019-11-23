package com.podo.pododev.web.domain.blog.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BlogTagService {

    private final BlogTagRepository blogTagRepository;

    public List<String> getAllDistinctTagValuesByBlogEnabledTrue() {
        return blogTagRepository.findDistinctTagValue();
    }
}
