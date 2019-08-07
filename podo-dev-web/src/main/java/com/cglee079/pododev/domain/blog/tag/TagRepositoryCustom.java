package com.cglee079.pododev.domain.blog.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepositoryCustom {
    List<String> findDistinctTagValue();
}
