package com.cglee079.pododev.web.domain.blog.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryCustom {
    Page<Comment> paging(Long blogId, Pageable pageable);
}
