package com.cglee079.pododev.web.domain.blog.attachfile;

import com.cglee079.pododev.web.domain.blog.comment.Comment;
import com.cglee079.pododev.web.domain.blog.comment.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
}

