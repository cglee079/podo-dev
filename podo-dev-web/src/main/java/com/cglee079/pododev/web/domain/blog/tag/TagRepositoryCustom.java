package com.cglee079.pododev.web.domain.blog.tag;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepositoryCustom {
    List<String> findDistinctTagValue();
    void deleteByBlogSeq(Long seq);
}
