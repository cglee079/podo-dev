package com.cglee079.pododev.web.domain.blog.comment;

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

        comments.forEach(comment -> commentRes.add(new CommentDto.response(comment)));

        return commentRes;
    }

    public void insert(Long blogSeq, CommentDto.insert insert) {
        final String username = insert.getUsername();
        final String password = insert.getPassword();
        final String contents = insert.getContents();
        final Long parentSeq = insert.getParentSeq();

        //새로운 댓글
        if (Objects.isNull(parentSeq)) {
            log.info("New Comment Insert");

            Comment comment = Comment.builder()
                    .blogSeq(blogSeq)
                    .username(username)
                    .password(password)
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
                    .password(password)
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
        Optional<Comment> comment = commentRepository.findById(seq);

        if(!comment.isPresent()){

        }
        //TODO 권한 인증

        comment.get().erase();
    }
}
