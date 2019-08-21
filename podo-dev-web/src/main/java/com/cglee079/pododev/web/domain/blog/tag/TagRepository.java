package com.cglee079.pododev.web.domain.blog.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom{
    List<Tag> findByBlogSeq(Long blogSeq);
    List<Tag> findByVal(String value);
}
