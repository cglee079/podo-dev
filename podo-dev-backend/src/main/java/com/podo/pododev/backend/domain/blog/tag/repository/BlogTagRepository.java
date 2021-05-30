package com.podo.pododev.backend.domain.blog.tag.repository;

import com.podo.pododev.backend.domain.blog.tag.model.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long>, BlogTagRepositoryCustom {
}
