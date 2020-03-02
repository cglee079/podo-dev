package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class CommentSetup {

    private final CommentRepository commentRepository;

    public Comment saveOne(User user, Blog blog){

        final Comment comment = Comment.builder()
                .parentId(null)
                .byAdmin(false)
                .contents("contents")
                .depth(1)
                .writer(user)
                .sort(BigDecimal.ONE)
                .childCount(0)
                .enabled(true)
                .build();

        final Comment saveComment = commentRepository.save(comment);
        comment.changeCgroup(comment.getId());
        blog.addComment(comment);

        return saveComment;
    }
}
