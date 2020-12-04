package com.podo.pododev.web.domain.blog.comment.application.helper;

import com.podo.pododev.web.domain.blog.comment.exception.InvalidCommentIdApiException;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class CommentServiceHelper {

    public static Comment findById(Long commentId, CommentRepository commentRepository) {
        final Optional<Comment> commentOptional = commentRepository.findById(commentId);
        return commentOptional.orElseThrow(() -> new InvalidCommentIdApiException(commentId));
    }
}
