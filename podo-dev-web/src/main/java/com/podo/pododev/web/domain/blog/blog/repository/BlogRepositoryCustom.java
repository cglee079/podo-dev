package com.podo.pododev.web.domain.blog.blog.repository;

import com.podo.pododev.web.domain.blog.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepositoryCustom {
    Blog findOneAfterPublishAt(LocalDateTime publishAt);

    Blog findOneBeforePublishAt(LocalDateTime publishAt);

    Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled);

    List<Blog> findByEnabledOrderByPublishAsc(Boolean enabled);

    List<Blog> findByWebFeeded(Boolean feeded);

    List<Blog> findByTagValues(String first, List<String> otherTags);

    List<Blog> findAllByEnabledAndOrderByPublishAtDesc(Boolean enabled);
}
