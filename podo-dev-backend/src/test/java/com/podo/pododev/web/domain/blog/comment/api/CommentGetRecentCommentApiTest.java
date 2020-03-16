package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.test.BlogSetup;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.test.UserSetup;
import com.podo.pododev.web.test.IntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"blog.comment.recent.size=2"})
@DisplayName("통합테스트, 최근 댓글 조회")
@RequiredArgsConstructor
class CommentGetRecentCommentApiTest extends IntegrationTest {

    private final UserSetup userSetup;
    private final BlogSetup blogSetup;
    private final CommentSetup commentSetup;

    @DisplayName("최근 댓글 조회")
    @Test
    void testGetRecentComments01() throws Exception {

        final User user = userSetup.saveOne();
        final Blog blog = blogSetup.saveOne();
        final Comment comment = commentSetup.saveOne(user, blog);

        mockMvc().perform(get("/api/comments/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.contents[0].contents").value(comment.getContents()))
                .andExpect(jsonPath("$.contents[0].writer.username").value(user.getUsername()))
                .andExpect(jsonPath("$.contents[0].blogId").value(blog.getId()));
    }

    @DisplayName("최근 댓글 조회 ( 정렬 , limit, enabled 확인) ")
    @Test
    void testGetRecentComments02() throws Exception {

        final User user = userSetup.saveOne();
        final Blog blog = blogSetup.saveOne();
        final Comment commentA = commentSetup.saveOne(user, blog);
        final Comment commentB = commentSetup.saveOne(user, blog);
        final Comment commentC = commentSetup.saveOne(user, blog);
        final Comment commentD = commentSetup.saveOne(user, blog);
        commentC.erase("delete!!!!");

        mockMvc().perform(get("/api/comments/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.contents[0].id").value(commentD.getId()))
                .andExpect(jsonPath("$.contents[1].id").value(commentB.getId()));
    }


}