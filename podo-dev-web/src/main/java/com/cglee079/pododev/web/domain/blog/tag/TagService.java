package com.cglee079.pododev.web.domain.blog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TagService {

    public final TagRepository tagRepository;


    public List<String> listValues() {
        return tagRepository.findDistinctTagValue();
    }
}
