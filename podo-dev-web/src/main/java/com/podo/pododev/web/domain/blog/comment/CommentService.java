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


    public void insert(Long blogId, CommentDto.insert insert) {

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
        log.info("New Comment Insert");

        final Comment newComment = Comment.builder()
                .blog(blog)
                .writer(user)
                .contents(contents)
                .childCount(0)
                .depth(0)
                .sort(1d)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        commentRepository.save(newComment);
        blog.addComment(newComment);

        newComment.changeCgroup(newComment.getId());
    }

    private void insertReplyComment(User user, Blog blog, String contents, Long parentCommentId) {
        log.info("Replay Comment Insert, parent '{}'", parentCommentId);

        final Comment parentComment = commentRepository.findById(parentCommentId).get();

        final Long cgroup = parentComment.getCgroup();
        final Integer depth = parentComment.getDepth();
        final Double childSort = parentComment.getChildCommentSort();

        if (parentComment.isExceedMaxCommentDepth(maxCommentDepth)) {
            throw new MaxDepthCommentException();
        }

        parentComment.increaseChildCount();

        final Comment newChildComment = Comment.builder()
                .blog(blog)
                .writer(user)
                .contents(contents)
                .cgroup(cgroup)
                .childCount(0)
                .parentId(parentCommentId)
                .depth(depth + 1)
                .sort(childSort)
                .byAdmin(SecurityUtil.isAdmin())
                .build();

        commentRepository.save(newChildComment);
        blog.addComment(newChildComment);
    }


    /**
     * 댓글 삭제
     */
    public void deleteByCommentId(Long commentId) {
        final String currentUserId = SecurityUtil.getUserId();

        // No Login Error
        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final Optional<Comment> existCommentOptional = commentRepository.findById(commentId);

        // No Comment Error
        if (!existCommentOptional.isPresent()) {
            throw new InvalidCommentException();
        }

        final Comment existCommand = existCommentOptional.get();

        // No Auth Error
        if (!existCommand.isCreateByUserId(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        //자식이 없는 경우
        if (!existCommand.hasChild()) {
            commentRepository.delete(existCommand);
            decreaseChild(existCommand.getParentId());
        }

        // 자식이 있는 경우
        else {
            existCommand.erase();
        }
    }


    private void decreaseChild(Long commendId) {
        if (Objects.nonNull(commendId)) {
            final Comment existedComment = commentRepository.findById(commendId).get();

            existedComment.decreaseChildCount();

            //삭제인 상태에서, 자식이 없는 경우 삭제
            if (!existedComment.hasChild() && existedComment.isErase()) {
                commentRepository.delete(existedComment);

                //부모 검증 (재귀)
                decreaseChild(existedComment.getParentId());
            }
        }
    }

}
