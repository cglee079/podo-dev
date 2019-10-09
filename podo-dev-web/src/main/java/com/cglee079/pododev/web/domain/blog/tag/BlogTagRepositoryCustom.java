package com.cglee079.pododev.web.domain.blog.tag;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogTagRepositoryCustom {
    List<String> findDistinctTagValue();
}
