package com.cglee079.pododev.domain.blog.comment;

import com.cglee079.pododev.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByBlogSeq(Long blogSeq);
}
