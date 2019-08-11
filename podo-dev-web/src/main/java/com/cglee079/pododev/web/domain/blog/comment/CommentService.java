package com.cglee079.pododev.web.domain.blog.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        String username = insert.getUsername();
        String password = insert.getPassword();
        String contents = insert.getContents();
        Long parentSeq = insert.getParentSeq();

        //새로운 댓글
        if (Objects.isNull(parentSeq)) {
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
            Comment parentComment = commentRepository.findById(parentSeq).get();
            Long cgroup = parentComment.getCgroup();
            Integer child = parentComment.getChild();
            Double sort = parentComment.getSort();
            Integer depth = parentComment.getDepth();

            parentComment.increaseChild();

            Double childSort = ((double) (child + 1) / Math.pow(10, 3 * depth)) + sort;

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

        //TODO 권한 인증

        comment.get().delete();
    }
}
