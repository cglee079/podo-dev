package com.podo.pododev.web.domain.blog.comment.application;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.application.helper.CommentServiceHelper;
import com.podo.pododev.web.domain.blog.comment.exception.MaxDepthCommentApiException;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.InvalidUserIdException;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedException;
import com.podo.pododev.web.global.config.cache.annotation.AllCommentCacheEvict;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentWriteService {

    @Value("${blog.comment.max.depth}")
    private Integer maxDepthOfComment;

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @AllCommentCacheEvict
    public void insertNewComment(Long blogId, CommentDto.insert insert) {

        final String currentUserKey = SecurityUtil.getUserKey();

        if (Objects.isNull(currentUserKey)) {
            throw new NoAuthenticatedException();
        }

        final User currentUser = getCurrentUser(currentUserKey);
        final Blog existedBlog = BlogServiceHelper.findByBlogId(blogId, blogRepository);

        final String commentContents = insert.getContents();
        final Long parentCommentId = insert.getParentId();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(currentUser, existedBlog, commentContents);
            return;
        }

        insertReplyComment(currentUser, existedBlog, commentContents, parentCommentId);
    }

    private User getCurrentUser(String currentUserKey) {
        final Optional<User> currentUserOptional = userRepository.findByUserKey(currentUserKey);
        return currentUserOptional.orElseThrow(() -> new InvalidUserIdException(currentUserKey));
    }

    private void insertNewComment(User user, Blog blog, String contents) {
        log.info("'{}' 게시글에 새로운 댓글이 등록되었습니다", blog.getTitle());

        final Comment newComment = Comment.builder()
                .blog(blog)
                .writer(user)
                .contents(contents)
                .childCount(0)
                .depth(0)
                .sort(1d)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        final Comment savedComment = commentRepository.save(newComment);
        blog.addComment(savedComment);

        savedComment.changeCgroup(savedComment.getId());
    }

    private void insertReplyComment(User user, Blog blog, String contents, Long parentCommentId) {
        log.info("'{}' 게시글에 새로운 답글이 등록되었습니다", blog.getTitle());

        final Comment parentComment = CommentServiceHelper.findById(parentCommentId, commentRepository);

        final Long parentCommentCgroup = parentComment.getCgroup();
        final Integer parentCommentDepth = parentComment.getDepth();
        final Double childCommentSort = parentComment.getChildCommentSort();

        if (parentComment.isExceedMaxCommentDepth(maxDepthOfComment)) {
            throw new MaxDepthCommentApiException(parentCommentId);
        }

        parentComment.increaseChildCount();

        final Comment newChildComment = Comment.builder()
                .blog(blog)
                .writer(user)
                .contents(contents)
                .cgroup(parentCommentCgroup)
                .childCount(0)
                .parentId(parentCommentId)
                .depth(parentCommentDepth + 1)
                .sort(childCommentSort)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        final Comment savedChildComment = commentRepository.save(newChildComment);
        blog.addComment(savedChildComment);
    }


    @AllCommentCacheEvict
    public void removeExistedCommentByCommentId(Long commentId) {
        final String currentUserId = SecurityUtil.getUserKey();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final Comment existedComment = CommentServiceHelper.findById(commentId, commentRepository);

        if (!existedComment.isWrittenBy(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        log.info("'{}' 댓글을 삭제합니다", existedComment.getId());

        if (existedComment.hasChild()) {
            existedComment.erase();
            return;
        }

        commentRepository.delete(existedComment);
        decreaseChildCountAndRemoveIfNoHasChild(existedComment.getParentId());
    }


    private void decreaseChildCountAndRemoveIfNoHasChild(Long commentId) {
        final Comment existedComment = CommentServiceHelper.findById(commentId, commentRepository);

        existedComment.decreaseChildCount();

        if (!existedComment.hasChild() && existedComment.isErase()) {
            log.info("'{}' 댓글은, 자식댓글이 없어 완전이 삭제합니다", commentId);
            commentRepository.delete(existedComment);
            decreaseChildCountAndRemoveIfNoHasChild(existedComment.getParentId());
        }
    }



}
