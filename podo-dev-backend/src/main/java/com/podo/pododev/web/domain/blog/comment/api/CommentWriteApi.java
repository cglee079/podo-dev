package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.web.domain.blog.comment.application.CommentWriteService;
import com.podo.pododev.web.domain.blog.comment.dto.CommentInsert;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentWriteApi {

    private final CommentWriteService commentWriteService;

    @PostMapping("/api/blogs/{blogId}/comments")
    public void insertNewComment(@PathVariable Long blogId, @Valid @RequestBody CommentInsert commentInsert) {
        commentWriteService.insertNewComment(blogId, commentInsert, LocalDateTime.now());
    }

    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public void removeByCommentId(@PathVariable Long commentId) {
        commentWriteService.removeByCommentId(commentId);
    }

}
