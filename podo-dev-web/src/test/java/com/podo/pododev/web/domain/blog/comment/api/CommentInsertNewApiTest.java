package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogSetup;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserSetup;
import com.podo.pododev.web.global.util.JsonUtil;
import com.podo.pododev.web.test.IntegrationTest;
import com.podo.pododev.web.test.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"blog.comment.recent.size=2"})
@DisplayName("통합테스트, 새로운 댓글 등록")
@RequiredArgsConstructor
class CommentInsertNewApiTest extends IntegrationTest {

    private final UserSetup userSetup;
    private final BlogSetup blogSetup;
    private final CommentSetup commentSetup;
    private final CommentRepository commentRepository;

    @DisplayName("새로운 댓글 입력")
    @Test
    void testInsertComment01() throws Exception {
        //given
        final User user = userSetup.saveOne();
        TestUtil.setAuth(user);

        final Blog blog = blogSetup.saveOne();
        final CommentDto.insert insert = JsonUtil.toObject(TestUtil.getStringFromResource("data", "comment", "insert_comment.json"), CommentDto.insert.class);

        //when
        mockMvc().perform(post("/api/blogs/" + blog.getId() + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(insert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(DefaultApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(DefaultApiStatus.SUCCESS.getMessage()));

        //then
        final List<Comment> comments = commentRepository.findAll();
        final Comment comment = comments.get(0);

        assertThat(comment).isNotNull();
        assertThat(comment.getContents()).isEqualTo(insert.getContents());
        assertThat(comment.getBlog().getId()).isEqualTo(blog.getId());
    }

    @DisplayName("새로운 댓글 입력, 자식 댓글인 경우")
    @Test
    void testInsertComment02() throws Exception {
        //given
        final User user = userSetup.saveOne();
        TestUtil.setAuth(user);

        final Blog blog = blogSetup.saveOne();
        final Comment existedComment = commentSetup.saveOne(user, blog);
        final CommentDto.insert insert = JsonUtil.toObject(TestUtil.getStringFromResource("data", "comment", "insert_comment.json"), CommentDto.insert.class);
        ReflectionTestUtils.setField(insert, "parentId", existedComment.getId());

        //when
        mockMvc().perform(post("/api/blogs/" + blog.getId() + "/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(insert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(DefaultApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(DefaultApiStatus.SUCCESS.getMessage()));

        //then
        final List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(2);

        final Comment comment = comments.get(1);
        assertThat(comment).isNotNull();
        assertThat(comment.getContents()).isEqualTo(insert.getContents());
        assertThat(comment.getBlog().getId()).isEqualTo(blog.getId());
        assertThat(comment.getCgroup()).isEqualTo(existedComment.getCgroup());
        assertThat(comment.getParentId()).isEqualTo(existedComment.getId());
        assertThat(comment.getChildCommentSort()).isEqualTo(existedComment.getSort());
    }


}