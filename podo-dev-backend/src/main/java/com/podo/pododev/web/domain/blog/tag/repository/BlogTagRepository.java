package com.podo.pododev.web.domain.blog.tag.repository;

import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long>, BlogTagRepositoryCustom {
}
