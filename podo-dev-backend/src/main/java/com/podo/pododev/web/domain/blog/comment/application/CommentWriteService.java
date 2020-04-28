package com.podo.pododev.web.domain.blog.comment.application;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.comment.application.helper.CommentServiceHelper;
import com.podo.pododev.web.domain.blog.comment.dto.CommentInsert;
import com.podo.pododev.web.domain.blog.comment.exception.MaxDepthCommentApiException;
import com.podo.pododev.web.domain.blog.comment.exception.NoAuthorizedCommentApiException;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.domain.user.repository.UserRepository;
import com.podo.pododev.web.domain.user.application.helper.UserServiceHelper;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.web.global.config.aop.notifier.CommentNotice;
import com.podo.pododev.web.global.config.cache.annotation.AllCommentCacheEvict;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentWriteService {

    public static final String DELETED_CONTENTS = "삭제된 댓글입니다.";

    @Value("${blog.comment.max.depth}")
    private final Integer maxDepthOfComment;

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @AllArgsNotNull
    @CommentNotice
    @AllCommentCacheEvict
    public void insertNewComment(Long blogId, CommentInsert insert) {
        SecurityUtil.validateIsAuth();

        final User currentUser = UserServiceHelper.getCurrentUser(SecurityUtil.getUserId(), userRepository);
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        final String commentContents = insert.getContents();
        final Long parentCommentId = insert.getParentId();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(currentUser, existedBlog, commentContents);
            return;
        }

        insertReplyComment(currentUser, existedBlog, commentContents, parentCommentId);
    }

    private void insertNewComment(User user, Blog blog, String contents) {
        log.debug("'{}' 게시글에 새로운 댓글이 등록되었습니다", blog.getTitle());

        final Comment newComment = Comment.builder()
                .writer(user)
                .contents(contents)
                .depth(0)
                .sort(BigDecimal.ONE)
                .childCount(0)
                .enabled(true)
                .build();

        final Comment savedComment = commentRepository.save(newComment);
        blog.addComment(savedComment);

        savedComment.changeCgroup(savedComment.getId());
    }

    private void insertReplyComment(User user, Blog blog, String contents, Long parentCommentId) {
        log.debug("'{}' 게시글에 새로운 답글이 등록되었습니다", blog.getTitle());

        final Comment parentComment = CommentServiceHelper.findById(parentCommentId, commentRepository);

        final Long parentCommentCgroup = parentComment.getCgroup();
        final Integer parentCommentDepth = parentComment.getDepth();
        final BigDecimal childCommentSort = parentComment.getChildCommentSort();

        if (parentComment.isExceedMaxCommentDepth(maxDepthOfComment)) {
            throw new MaxDepthCommentApiException(parentCommentId);
        }

        parentComment.increaseChildCount();

        final Comment newChildComment = Comment.builder()
                .writer(user)
                .contents(contents)
                .cgroup(parentCommentCgroup)
                .parentId(parentCommentId)
                .depth(parentCommentDepth + 1)
                .sort(childCommentSort)
                .childCount(0)
                .enabled(true)
                .build();

        final Comment savedChildComment = commentRepository.save(newChildComment);
        blog.addComment(savedChildComment);
    }


    @AllCommentCacheEvict
    public void removeByCommentId(Long commentId) {
        SecurityUtil.validateIsAuth();

        final Comment comment = CommentServiceHelper.findById(commentId, commentRepository);

        if (!comment.isWrittenBy(SecurityUtil.getUserId())) {
            throw new NoAuthorizedCommentApiException(commentId);
        }

        log.debug("'{}' 댓글을 삭제합니다", comment.getId());

        comment.erase(DELETED_CONTENTS);

        if (!comment.canDeleted()) {
            return;
        }

        comment.changeBlog(null);
        commentRepository.delete(comment);

        decreaseChildCountAndRemoveIfCanDeleted(comment.getParentId());
    }


    private void decreaseChildCountAndRemoveIfCanDeleted(Long commentId) {
        if(Objects.isNull(commentId)){
            return;
        }

        final Comment existedComment = CommentServiceHelper.findById(commentId, commentRepository);

        existedComment.decreaseChildCount();

        if (!existedComment.canDeleted()) {
            return;
        }

        log.debug("'{}' 댓글은, 자식댓글이 없어 완전이 삭제합니다", commentId);

        commentRepository.delete(existedComment);
        decreaseChildCountAndRemoveIfCanDeleted(existedComment.getParentId());
    }
}
