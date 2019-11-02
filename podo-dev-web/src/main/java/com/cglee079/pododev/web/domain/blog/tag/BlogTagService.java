package com.cglee079.pododev.web.domain.blog.tag;

import com.cglee079.pododev.web.global.util.ChosungUtil;
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

    public List<String> getAll() {
        List<String> values = blogTagRepository.findDistinctTagValue();

        return values;
    }
}
