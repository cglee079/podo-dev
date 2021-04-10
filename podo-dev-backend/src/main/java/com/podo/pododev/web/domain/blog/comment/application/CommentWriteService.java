package com.podo.pododev.web.domain.blog.comment.application;

import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.comment.application.helper.CommentServiceHelper;
import com.podo.pododev.web.domain.blog.comment.dto.CommentInsert;
import com.podo.pododev.web.domain.blog.comment.exception.MaxDepthCommentApiException;
import com.podo.pododev.web.domain.blog.comment.exception.NoAuthorizedCommentApiException;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.application.helper.UserServiceHelper;
import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.domain.user.repository.UserRepository;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.web.global.config.cache.annotation.AllCommentCacheEvict;
import com.podo.pododev.web.global.context.ThreadLocalContext;
import com.podo.pododev.web.global.event.ReplyCommentNotifyDto;
import com.podo.pododev.web.global.event.ReplyCommentNotifyPublisher;
import com.podo.pododev.web.global.event.UserCommentNotifyDto;
import com.podo.pododev.web.global.event.UserCommentNotifyPublisher;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final UserCommentNotifyPublisher userCommentNotifyPublisher;
    private final ReplyCommentNotifyPublisher replyCommentNotifyPublisher;

    @AllArgsNotNull
    @AllCommentCacheEvict
    public void insertNewComment(Long blogId, CommentInsert insert, LocalDateTime now) {
        SecurityUtil.validateIsAuth();

        final User currentUser = UserServiceHelper.getCurrentUser(SecurityUtil.getUserId(), userRepository);
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        final String commentContents = insert.getContents();
        final Long parentCommentId = insert.getParentId();
        final Boolean notified = insert.getNotified();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(currentUser, existedBlog, commentContents, notified);
            notifyUserCommentToAdmin(existedBlog, insert.getContents(), now);
            return;
        }

        insertReplyComment(currentUser, existedBlog, commentContents, parentCommentId, notified, now);
        notifyUserCommentToAdmin(existedBlog, insert.getContents(), now);
    }

    private void insertNewComment(User user, Blog blog, String contents, Boolean notified) {
        final Comment newComment = Comment.builder()
                .writer(user)
                .contents(contents)
                .depth(0)
                .sort(BigDecimal.ONE)
                .childCount(0)
                .notified(notified)
                .enabled(true)
                .build();

        final Comment savedComment = commentRepository.save(newComment);
        blog.addComment(savedComment);

        savedComment.changeCgroup(savedComment.getId());
    }

    private void insertReplyComment(User user, Blog blog, String contents, Long parentCommentId, Boolean notified, LocalDateTime now) {
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
                .notified(notified)
                .enabled(true)
                .build();

        final Comment savedChildComment = commentRepository.save(newChildComment);
        blog.addComment(savedChildComment);

        if (parentComment.isNotified() && !StringUtils.isEmpty(parentComment.getWriter().getEmail())) {
            replyCommentNotifyPublisher.publishReplyCommentNotify(
                    ReplyCommentNotifyDto.builder()
                            .blogId(blog.getId())
                            .email(parentComment.getWriter().getEmail())
                            .contents(contents)
                            .writer(user.getUsername())
                            .writeAt(now)
                            .originContents(parentComment.getContents())
                            .originWriter(parentComment.getWriter().getUsername())
                            .originWriteAt(parentComment.getCreateAt())
                            .build()
            );
        }
    }

    private void notifyUserCommentToAdmin(Blog existedBlog, String commentContents, LocalDateTime now) {
        if (SecurityUtil.isAdmin()) {
            return;
        }

        userCommentNotifyPublisher.publishCommentNotify(
                UserCommentNotifyDto.builder()
                        .blogTitle(existedBlog.getTitle())
                        .username(SecurityUtil.getUsername())
                        .content(commentContents)
                        .writeAt(now)
                        .build()
        );
    }

    @AllCommentCacheEvict
    public void removeByCommentId(Long commentId) {
        SecurityUtil.validateIsAuth();

        final Comment comment = CommentServiceHelper.findById(commentId, commentRepository);

        if (!comment.isWrittenBy(SecurityUtil.getUserId())) {
            throw new NoAuthorizedCommentApiException(commentId);
        }

        ThreadLocalContext.debug(String.format("'%s' 댓글을 삭제합니다", comment.getId()));

        comment.erase(DELETED_CONTENTS);

        if (!comment.canDeleted()) {
            return;
        }

        comment.changeBlog(null);
        commentRepository.delete(comment);

        decreaseChildCountAndRemoveIfCanDeleted(comment.getParentId());
    }


    private void decreaseChildCountAndRemoveIfCanDeleted(Long commentId) {
        if (Objects.isNull(commentId)) {
            return;
        }

        final Comment existedComment = CommentServiceHelper.findById(commentId, commentRepository);

        existedComment.decreaseChildCount();

        if (!existedComment.canDeleted()) {
            return;
        }

        ThreadLocalContext.debug(String.format("'%s' 댓글은, 자식댓글이 없어 완전이 삭제합니다", commentId));

        commentRepository.delete(existedComment);
        decreaseChildCountAndRemoveIfCanDeleted(existedComment.getParentId());
    }
}
