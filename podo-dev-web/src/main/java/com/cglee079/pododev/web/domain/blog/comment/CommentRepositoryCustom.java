package com.cglee079.pododev.web.domain.blog.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    Page<Comment> paging(Long blogSeq, Pageable pageable);
}
