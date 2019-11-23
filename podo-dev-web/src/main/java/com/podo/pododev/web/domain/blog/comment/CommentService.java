package com.podo.pododev.web.domain.blog.comment;

import com.podo.pododev.core.rest.response.PageDto;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserRepository;
import com.podo.pododev.web.domain.user.exception.NoAuthenticatedException;
import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.comment.exception.InvalidCommentException;
import com.podo.pododev.web.domain.blog.comment.exception.MaxDepthCommentException;
import com.podo.pododev.web.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    @Value("${blog.comment.max.depth}")
    private Integer maxCommentDepth;

    @Value("${blog.comment.per.page.size}")
    private Integer pageSize;

    @Value("${blog.comment.recent.size}")
    private Integer recentCommentSize;

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentDto.summary> getRecentComments() {
        final List<Comment> comments = commentRepository.findRecentComments(recentCommentSize);

        return comments.stream()
                .map(CommentDto.summary::new)
                .collect(Collectors.toList());
    }

    public PageDto<CommentDto.response> paging(Long blogId, CommentDto.request request) {

        final int newPage = reversePage(request.getPage(), commentRepository.countByBlogId(blogId));

        final Pageable pageable = PageRequest.of(newPage, pageSize);
        final Page<Comment> comments = commentRepository.paging(blogId, pageable);

        final List<CommentDto.response> commentResponses = comments.stream()
                .map(comment -> new CommentDto.response(comment, SecurityUtil.getUserId()))
                .collect(Collectors.toList());

        return PageDto.<CommentDto.response>builder()
                .data(commentResponses)
                .currentPage(comments.getPageable().getPageNumber())
                .pageSize(comments.getPageable().getPageSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .build();
    }

    private int reversePage(Integer page, double count) {
        double totalPage = Math.ceil((count / (double) pageSize));
        if (totalPage == 0) {
            totalPage = 1;
        }
        return (int) (totalPage - page - 1);
    }


    public void insertNewComment(Long blogId, CommentDto.insert insert) {

        final String currentUserId = SecurityUtil.getUserId();
        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final User currentUser = userRepository.findByUserId(currentUserId).get();
        final Blog existedBlog = blogRepository.findById(blogId).get();
        final String commentContents = insert.getContents();
        final Long parentCommentId = insert.getParentId();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(currentUser, existedBlog, commentContents);
        } else {
            insertReplyComment(currentUser, existedBlog, commentContents, parentCommentId);
        }


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

        final Comment parentComment = commentRepository.findById(parentCommentId).get();

        final Long parentCommentCgroup = parentComment.getCgroup();
        final Integer parentCommentDepth = parentComment.getDepth();
        final Double childCommentSort = parentComment.getChildCommentSort();

        if (parentComment.isExceedMaxCommentDepth(maxCommentDepth)) {
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


    public void removeExistedCommentByCommentId(Long commentId) {
        final String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final Optional<Comment> existCommentOptional = commentRepository.findById(commentId);

        if (!existCommentOptional.isPresent()) {
            throw new InvalidCommentException();
        }

        final Comment existCommand = existCommentOptional.get();

        if (!existCommand.isWritedBy(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        if (!existCommand.hasChild()) {
            commentRepository.delete(existCommand);
            decreaseChildCountAndRemoveIfNoHasChild(existCommand.getParentId());
        } else {
            existCommand.erase();
        }
    }


    private void decreaseChildCountAndRemoveIfNoHasChild(Long commendId) {
        if (Objects.nonNull(commendId)) {
            final Comment existedComment = commentRepository.findById(commendId).get();

            existedComment.decreaseChildCount();

            if (!existedComment.hasChild() && existedComment.isErase()) {
                commentRepository.delete(existedComment);
                decreaseChildCountAndRemoveIfNoHasChild(existedComment.getParentId());
            }
        }
    }

}
