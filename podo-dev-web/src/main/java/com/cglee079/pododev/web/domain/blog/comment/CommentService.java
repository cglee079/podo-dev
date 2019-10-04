package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.core.global.response.PageDto;
import com.cglee079.pododev.web.domain.auth.exception.NoAuthenticatedException;
import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.blog.BlogRepository;
import com.cglee079.pododev.web.domain.blog.comment.exception.InvalidCommentException;
import com.cglee079.pododev.web.domain.blog.comment.exception.MaxDepthCommentException;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Value("${blog.comment.max.depth}")
    private Integer maxCommentDepth;

    @Value("${blog.comment.per.page.size}")
    private Integer pageSize;

    public PageDto<CommentDto.response> paging(Long blogSeq, CommentDto.request request) {

        final Integer page = request.getPage();

        //Reverse Page
        final int count = this.commentRepository.countByBlogSeq(blogSeq);
        double totalPage = Math.ceil(((double) count / (double) pageSize));
        if (totalPage == 0) {
            totalPage = 1;
        }
        final int newPage = (int) (totalPage - page - 1);

        final Pageable pageable = PageRequest.of(newPage, pageSize);
        final Page<Comment> comments = commentRepository.paging(blogSeq, pageable);
        final List<CommentDto.response> commentRes = new LinkedList<>();

        comments.forEach(comment -> commentRes.add(new CommentDto.response(comment, SecurityUtil.getUserId())));

        return PageDto.<CommentDto.response>builder()
                .contents(commentRes)
                .currentPage(comments.getPageable().getPageNumber())
                .pageSize(comments.getPageable().getPageSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .build();
    }

    public void insert(Long blogSeq, CommentDto.insert insert) {

        final String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }


        final Blog blog = blogRepository.findById(blogSeq).get();
        final String userId = currentUserId;
        final String username = SecurityUtil.getUsername();
        final String contents = insert.getContents();
        final Long parentSeq = insert.getParentSeq();

        // New Comment
        if (Objects.isNull(parentSeq)) {
            log.info("New Comment Insert");

            Comment comment = Comment.builder()
                    .blog(blog)
                    .username(username)
                    .userId(userId)
                    .contents(contents)
                    .child(0)
                    .depth(0)
                    .sort(Double.valueOf(1))
                    .build();

            comment = commentRepository.save(comment);
            comment.updateCgroup(comment.getSeq());

        }

        // New Reply
        else {
            log.info("Replay Comment Insert, parent '{}'", parentSeq);

            final Comment parentComment = commentRepository.findById(parentSeq).get();

            final Long cgroup = parentComment.getCgroup();
            final Integer child = parentComment.getChild();
            final Double sort = parentComment.getSort();
            final Integer depth = parentComment.getDepth();
            final Double childSort = ((double) (child + 1) / Math.pow(10, 3 * depth)) + sort;

            if ((depth + 1) > maxCommentDepth) {
                throw new MaxDepthCommentException();
            }

            parentComment.increaseChild();

            Comment comment = Comment.builder()
                    .blog(blog)
                    .username(username)
                    .userId(userId)
                    .contents(contents)
                    .cgroup(cgroup)
                    .child(0)
                    .parentSeq(parentSeq)
                    .depth(depth + 1)
                    .sort(childSort)
                    .build();

            commentRepository.save(comment);
        }


    }

    /**
     * 댓글 삭제
     */
    public void delete(Long seq) {
        final String currentUserId = SecurityUtil.getUserId();

        // No Login Error
        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        Optional<Comment> commentOpt = commentRepository.findById(seq);

        // No Comment Error
        if (!commentOpt.isPresent()) {
            throw new InvalidCommentException();
        }

        Comment comment = commentOpt.get();

        // No Auth Error
        if (!comment.getUserId().equals(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        //자식이 없는 경우
        if (comment.getChild() == 0) {

            commentRepository.delete(comment);
            decreaseChild(comment.getParentSeq());
        }

        // 자식이 있는 경우
        else {
            comment.erase();
        }
    }


    public void decreaseChild(Long seq) {
        if (!Objects.isNull(seq)) {
            Comment comment = commentRepository.findById(seq).get();
            comment.decreaseChild();

            //삭제인 상태에서, 자식이 없는 경우 삭제
            if (comment.getChild() == 0 && comment.isErase()) {
                commentRepository.delete(comment);

                //부모 검증 (재귀)
                decreaseChild(comment.getParentSeq());
            }
        }
    }


}
