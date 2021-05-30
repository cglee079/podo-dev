package com.podo.pododev.backend.domain.blog.tag.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogTagRepositoryCustom {
    List<String> findDistinctTagValue(boolean blogEnabled);
}
