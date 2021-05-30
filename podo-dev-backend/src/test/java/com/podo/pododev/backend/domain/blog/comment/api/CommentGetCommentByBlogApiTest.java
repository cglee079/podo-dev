package com.podo.pododev.backend.domain.blog.comment.api;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.comment.model.Comment;
import com.podo.pododev.backend.domain.user.model.User;
import com.podo.pododev.backend.test.BlogSetup;
import com.podo.pododev.backend.test.IntegrationTest;
import com.podo.pododev.backend.test.UserSetup;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"blog.comment.page.size=2"})
@DisplayName("통합테스트, 블로그 게시글별 댓글 조회")
@RequiredArgsConstructor
class CommentGetCommentByBlogApiTest extends IntegrationTest {

    private final UserSetup userSetup;
    private final BlogSetup blogSetup;
    private final CommentSetup commentSetup;

    @DisplayName("블로그 댓글 조회")
    @Test
    void testGetCommentByBlog01() throws Exception {
        //given
        final int page = 0;
        final User user = userSetup.saveOne();
        final Blog blog = blogSetup.saveOne();
        final Long blogId = blog.getId();
        final Comment commentA = commentSetup.saveOne(user, blog);
        final Comment commentB = commentSetup.saveOne(user, blog);

        //when, then
        mockMvc().perform(get("/api/blogs/" + blogId + "/comments?page=" + page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(page))
                .andExpect(jsonPath("$.contents[0].id").value(commentB.getId()))
                .andExpect(jsonPath("$.contents[1].id").value(commentA.getId()));
    }

    @DisplayName("블로그 댓글 조회, paging 확인")
    @Test
    void testGetCommentByBlog02() throws Exception {
        //given
        final int page = 1;
        final User user = userSetup.saveOne();
        final Blog blog = blogSetup.saveOne();
        final Long blogId = blog.getId();
        final Comment commentA = commentSetup.saveOne(user, blog);
        final Comment commentB = commentSetup.saveOne(user, blog);
        final Comment commentC = commentSetup.saveOne(user, blog);
        final Comment commentD = commentSetup.saveOne(user, blog);
        final Comment commentE = commentSetup.saveOne(user, blog);
        final Comment commentF = commentSetup.saveOne(user, blog);

        //when, then
        mockMvc().perform(get("/api/blogs/" + blogId + "/comments?page=" + page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(page))
                .andExpect(jsonPath("$.contents[0].id").value(commentD.getId()))
                .andExpect(jsonPath("$.contents[1].id").value(commentC.getId()));
    }


}
