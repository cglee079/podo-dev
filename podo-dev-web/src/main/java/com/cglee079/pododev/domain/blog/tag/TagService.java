package com.cglee079.pododev.domain.blog.tag;

import com.cglee079.pododev.domain.blog.Blog;
import com.cglee079.pododev.domain.blog.BlogDto;
import com.cglee079.pododev.domain.blog.BlogRepository;
import com.cglee079.pododev.global.response.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TagService {

    public final TagRepository tagRepository;


    public List<String> listValues() {
        return tagRepository.findDistinctTagValue();
    }
}
