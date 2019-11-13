package com.podo.pododev.web.domain.blog.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long>, BlogTagRepositoryCustom {
}
