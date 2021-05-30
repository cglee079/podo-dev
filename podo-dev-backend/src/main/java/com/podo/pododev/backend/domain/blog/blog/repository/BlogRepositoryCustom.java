package com.podo.pododev.backend.domain.blog.blog.repository;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepositoryCustom {
    Blog findOneAfterPublishAt(LocalDateTime publishAt, Boolean enabled);

    Blog findOneBeforePublishAt(LocalDateTime publishAt, Boolean enabled);

    Page<Blog> paging(Pageable pageable, Boolean enabled);

    Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled);

    List<Blog> findByEnabledOrderByPublishAsc(Boolean enabled);

    List<Blog> findByWebFeeded(Boolean feeded);

    List<Blog> findByTagValues(List<String> tagValues, Boolean enabled);

    List<Blog> findAllByEnabledAndOrderByPublishAtDesc(Boolean enabled);
}
