package com.cglee079.pododev.web.domain.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepositoryCustom {
    Blog findNext(Long id);

    Blog findBefore(Long id);

    Page<Blog> paging(Pageable pageable, List<Long> ids, Boolean enabled);

    List<Blog> findByEnabled(Boolean enabled);

    List<Blog> findByFeeded(Boolean feeded);

    List<Blog> findBlogByTagValue(String tagValue);
}
