package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.domain.auth.exception.NoAuthenticatedException;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final CommentRepository commentRepository;

    public List<CommentDto.response> list(Long blogSeq) {
        List<Comment> comments = commentRepository.findByBlogSeq(blogSeq);
        List<CommentDto.response> commentRes = new LinkedList<>();

        comments.forEach(comment -> commentRes.add(new CommentDto.response(comment, SecurityUtil.getUserId())));

        return commentRes;
    }

    public void insert(Long blogSeq, CommentDto.insert insert) {
        String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        final String userId = currentUserId;
        final String username = insert.getUsername();
        final String contents = insert.getContents();
        final Long parentSeq = insert.getParentSeq();

        //새로운 댓글
        if (Objects.isNull(parentSeq)) {
            log.info("New Comment Insert");

            Comment comment = Comment.builder()
                    .blogSeq(blogSeq)
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

        //댓글의 답글
        else {
            log.info("Replay Comment Insert, parent '{}'", parentSeq);

            final Comment parentComment = commentRepository.findById(parentSeq).get();
            final Long cgroup = parentComment.getCgroup();
            final Integer child = parentComment.getChild();
            final Double sort = parentComment.getSort();
            final Integer depth = parentComment.getDepth();
            final Double childSort = ((double) (child + 1) / Math.pow(10, 3 * depth)) + sort;

            parentComment.increaseChild();

            Comment comment = Comment.builder()
                    .blogSeq(blogSeq)
                    .username(username)
                    .userId(userId)
                    .contents(contents)
                    .cgroup(cgroup)
                    .child(0)
                    .depth(depth + 1)
                    .sort(childSort)
                    .build();

            commentRepository.save(comment);
        }


    }

    public void delete(Long seq) {
        String currentUserId = SecurityUtil.getUserId();

        if (Objects.isNull(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        Optional<Comment> commentOpt = commentRepository.findById(seq);

        if (!commentOpt.isPresent()) {
            //TODO 권한 인증
        }

        Comment comment = commentOpt.get();

        if (!comment.getUserId().equals(currentUserId)) {
            throw new NoAuthenticatedException();
        }

        comment.erase();

    }
}
