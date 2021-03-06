package com.podo.pododev.web.domain.blog.comment.repository;

import com.podo.pododev.web.domain.blog.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    int countByBlogId(Long blogId);
}

