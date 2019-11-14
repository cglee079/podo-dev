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

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Value("${blog.comment.max.depth}")
    private Integer maxCommentDepth;

    @Value("${blog.comment.per.page.size}")
    private Integer pageSize;

    @Value("${blog.comment.recent.size}")
    private Integer recentCommentSize;

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

        final List<CommentDto.response> commentRes = comments.stream()
                .map(comment -> new CommentDto.response(comment, SecurityUtil.getUserId()))
                .collect(Collectors.toList());

        return PageDto.<CommentDto.response>builder()
                .contents(commentRes)
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


    public void insert(Long blogId, CommentDto.insert insert) {

        final String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final User user = userRepository.findByUserId(currentUserId).get();
        final Blog blog = blogRepository.findById(blogId).get();
        final String contents = insert.getContents();
        final Long parentCommentId = insert.getParentId();

        if (Objects.isNull(parentCommentId)) {
            insertNewComment(user, blog, contents);
        } else {
            insertReplyComment(user, blog, contents, parentCommentId);
        }


    }

    private void insertNewComment(User user, Blog blog, String contents) {
        log.info("New Comment Insert");

        final Comment comment = Comment.builder()
                .blog(blog)
                .user(user)
                .contents(contents)
                .child(0)
                .depth(0)
                .sort(1d)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        insertComment(blog, comment);

        comment.updateCgroup(comment.getId());
    }

    private void insertReplyComment(User user, Blog blog, String contents, Long parentCommentId) {
        log.info("Replay Comment Insert, parent '{}'", parentCommentId);

        final Comment parentComment = commentRepository.findById(parentCommentId).get();

        final Long cgroup = parentComment.getCgroup();
        final Integer depth = parentComment.getDepth();
        final Double childSort = parentComment.getChildSort();

        if (parentComment.exceedMaxCommentDepth(maxCommentDepth)) {
            throw new MaxDepthCommentException();
        }

        parentComment.increaseChild();

        final Comment childComment = Comment.builder()
                .blog(blog)
                .user(user)
                .contents(contents)
                .cgroup(cgroup)
                .child(0)
                .parentId(parentCommentId)
                .depth(depth + 1)
                .sort(childSort)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        insertComment(blog, childComment);
    }

    private void insertComment(Blog blog, Comment comment) {
        commentRepository.save(comment);
        blog.addComment(comment);
    }

    /**
     * 댓글 삭제
     */
    public void delete(Long id) {
        final String currentUserId = SecurityUtil.getUserId();

        // No Login Error
        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        Optional<Comment> commentOpt = commentRepository.findById(id);

        // No Comment Error
        if (!commentOpt.isPresent()) {
            throw new InvalidCommentException();
        }

        final Comment comment = commentOpt.get();

        // No Auth Error
        if (!comment.getCreateBy().equals(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        //자식이 없는 경우
        if (comment.getChild() == 0) {
            commentRepository.delete(comment);
            decreaseChild(comment.getParentId());
        }

        // 자식이 있는 경우
        else {
            comment.erase();
        }
    }


    public void decreaseChild(Long id) {
        if (!Objects.isNull(id)) {
            Comment comment = commentRepository.findById(id).get();
            comment.decreaseChild();

            //삭제인 상태에서, 자식이 없는 경우 삭제
            if ((comment.getChild() == 0) && comment.isErase()) {
                commentRepository.delete(comment);

                //부모 검증 (재귀)
                decreaseChild(comment.getParentId());
            }
        }
    }
}
