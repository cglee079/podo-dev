package com.cglee079.pododev.web.domain.blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepositoryCustom {
    Blog findNext(Long seq);
    Blog findBefore(Long seq);
    Page<Blog> paging(Pageable pageable, List<Long> seqs, Boolean enabled);
    List<Long> findEnabledIds();
}
