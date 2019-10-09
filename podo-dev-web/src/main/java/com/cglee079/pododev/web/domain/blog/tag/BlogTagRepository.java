package com.cglee079.pododev.web.domain.blog.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long>, BlogTagRepositoryCustom {
    List<BlogTag> findByBlogSeq(Long blogSeq);
    List<BlogTag> findByVal(String value);
}
