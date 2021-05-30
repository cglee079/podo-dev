package com.podo.pododev.backend.domain.blog.comment.application;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.comment.api.CommentSetup;
import com.podo.pododev.backend.domain.blog.comment.dto.CommentInsert;
import com.podo.pododev.backend.domain.blog.comment.exception.NoAuthorizedCommentApiException;
import com.podo.pododev.backend.domain.blog.comment.model.Comment;
import com.podo.pododev.backend.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.global.util.JsonMapper;
import com.podo.pododev.backend.test.BlogSetup;
import com.podo.pododev.backend.test.IntegrationTest;
import com.podo.pododev.backend.test.TestUtil;
import com.podo.pododev.backend.test.UserSetup;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("통합테스트, 새로운 댓글 등록")
class CommentWriteServiceIntegrationTest extends IntegrationTest {

    private final UserSetup userSetup;
    private final BlogSetup blogSetup;
    private final CommentSetup commentSetup;
    private final CommentRepository commentRepository;
    private final CommentWriteService commentWriteService;

    @DisplayName("새로운 댓글 입력")
    @Test
    void testInsertComment01() {

        //given - user
        final User user = userSetup.saveOne();
        TestUtil.setAuth(user);

        //given - blog
        final Blog blog = blogSetup.saveOne();

        //given - comment insert
        final CommentInsert insert = JsonMapper.toObject(TestUtil.getStringFromResource("data", "comment", "insert_comment.json"), CommentInsert.class);

        //when
        commentWriteService.insertNewComment(blog.getId(), insert, LocalDateTime.now());

        //then
        final List<Comment> comments = commentRepository.findAll();
        final Comment comment = comments.get(0);

        assertThat(comment).isNotNull();
        assertThat(comment.getContents()).isEqualTo(insert.getContents());
        assertThat(comment.getBlog().getId()).isEqualTo(blog.getId());
    }

    @DisplayName("새로운 댓글 입력, 자식 댓글인 경우")
    @Test
    void testInsertComment02() {
        //given - user
        final User user = userSetup.saveOne();
        TestUtil.setAuth(user);

        //given - blog
        final Blog blog = blogSetup.saveOne();

        //given - existed comment
        final Comment existedComment = commentSetup.saveOne(user, blog);

        //given - comment insert
        final CommentInsert insert = JsonMapper.toObject(TestUtil.getStringFromResource("data", "comment", "insert_comment.json"), CommentInsert.class);
        final BigDecimal childCommentSort = existedComment.getChildCommentSort();
        ReflectionTestUtils.setField(insert, "parentId", existedComment.getId());

        //when
        commentWriteService.insertNewComment(blog.getId(), insert, LocalDateTime.now());

        //then
        final List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(2);

        final Comment comment = comments.get(1);
        assertThat(comment).isNotNull();
        assertThat(comment.getContents()).isEqualTo(insert.getContents());
        assertThat(comment.getBlog().getId()).isEqualTo(blog.getId());
        assertThat(comment.getCgroup()).isEqualTo(existedComment.getCgroup());
        assertThat(comment.getParentId()).isEqualTo(existedComment.getId());
        assertThat(comment.getSort()).isEqualTo(childCommentSort);
    }

    @DisplayName("댓글 삭제")
    @Test
    void testDeleteComment01() {
        //given - user & auth
        final User user = userSetup.saveOne();
        TestUtil.setAuth(user);

        //given - blog
        final Blog blog = blogSetup.saveOne();

        //given - existed comment
        final Comment comment = commentSetup.saveOne(user, blog);

        //when
        commentWriteService.removeByCommentId(comment.getId());

        //then
        final List<Comment> comments = commentRepository.findAll();
        assertThat(comments.isEmpty()).isTrue();
    }

    @DisplayName("댓글 삭제, 권한 없음")
    @Test
    void testDeleteComment02() {
        //given - auth
        final User authUser = userSetup.saveOne();
        TestUtil.setAuth(authUser);

        //given - blog
        final Blog blog = blogSetup.saveOne();

        //given - comment by another
        final User anotherUser = userSetup.saveOne();
        final Comment comment = commentSetup.saveOne(anotherUser, blog);

        //then
        assertThrows(NoAuthorizedCommentApiException.class, () -> commentWriteService.removeByCommentId(comment.getId()));
    }
}
