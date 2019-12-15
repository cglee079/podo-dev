package com.podo.pododev.web.domain.blog.comment.service;

import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.exception.InvalidCommentIdException;
import com.podo.pododev.web.domain.blog.comment.exception.MaxDepthCommentException;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.podo.pododev.web.domain.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.InvalidUserIdException;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedException;
import com.podo.pododev.web.global.config.cache.annotation.AllCommentCacheEvict;
import com.podo.pododev.web.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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

        final String currentUserId = SecurityUtil.getUserId();
        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final User currentUser = getCurrentUser(currentUserId);
        final Blog existedBlog = getBlogByBlogId(blogId);

        final String commentContents = insert.getContents();
        final Long parentCommentId = insert.getParentId();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(currentUser, existedBlog, commentContents);
            return;
        }

        insertReplyComment(currentUser, existedBlog, commentContents, parentCommentId);
    }

    private User getCurrentUser(String currentUserId) {
        final Optional<User> currentUserOptional = userRepository.findByUserId(currentUserId);

        if (!currentUserOptional.isPresent()) {
            throw new InvalidUserIdException(currentUserId);
        }

        return currentUserOptional.get();
    }

    private Blog getBlogByBlogId(Long blogId) {
        final Optional<Blog> existedBlogOptional = blogRepository.findById(blogId);

        if (!existedBlogOptional.isPresent()) {
            throw new InvalidBlogIdException(blogId);
        }

        return existedBlogOptional.get();
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

        final Comment parentComment = getCommentByCommentId(parentCommentId);

        final Long parentCommentCgroup = parentComment.getCgroup();
        final Integer parentCommentDepth = parentComment.getDepth();
        final Double childCommentSort = parentComment.getChildCommentSort();

        if (parentComment.isExceedMaxCommentDepth(maxDepthOfComment)) {
            throw new MaxDepthCommentException();
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
        final String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final Comment existedComment = getCommentByCommentId(commentId);

        if (!existedComment.isWritedBy(currentUserId)) {
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
        if (Objects.isNull(commentId)) {
            return;
        }

        final Comment existedComment = getCommentByCommentId(commentId);

        existedComment.decreaseChildCount();

        if (!existedComment.hasChild() && existedComment.isErase()) {
            log.info("'{}' 댓글은, 자식댓글이 없어 완전이 삭제합니다", commentId);
            commentRepository.delete(existedComment);
            decreaseChildCountAndRemoveIfNoHasChild(existedComment.getParentId());
        }
    }

    private Comment getCommentByCommentId(Long commentId) {
        final Optional<Comment> existedCommentOptional = commentRepository.findById(commentId);

        if (!existedCommentOptional.isPresent()) {
            throw new InvalidCommentIdException(commentId);
        }

        return existedCommentOptional.get();
    }

}
