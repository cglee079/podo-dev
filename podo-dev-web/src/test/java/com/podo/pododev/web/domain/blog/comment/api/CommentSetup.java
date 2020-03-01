package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CommentSetup {

    private final CommentRepository commentRepository;

    public List<Comment> save(User user, Blog blog, int size){
        final List<Comment> comments = new ArrayList<>();

        for(int i =0; i < size; i++){
            Comment comment  = Comment.builder()
                    .parentId(null)
                    .byAdmin(false)
                    .contents("contents")
                    .depth(1)
                    .writer(user)
                    .sort(1d)
                    .childCount(0)
                    .enabled(true)
                    .build();

            comment.changeBlog(blog);
            comments.add(commentRepository.save(comment));
        }


        return comments;
    }
}
