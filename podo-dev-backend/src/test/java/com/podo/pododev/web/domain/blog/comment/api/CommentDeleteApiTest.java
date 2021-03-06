//package com.podo.pododev.web.domain.blog.comment.api;
//
//import com.podo.pododev.web.domain.blog.blog.Blog;
//import com.podo.pododev.web.test.BlogSetup;
//import com.podo.pododev.web.domain.blog.comment.Comment;
//import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
//import com.podo.pododev.web.domain.user.User;
//import com.podo.pododev.web.test.UserSetup;
//import com.podo.pododev.web.test.IntegrationTest;
//import com.podo.pododev.web.test.TestUtil;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@TestPropertySource(properties = {"blog.comment.recent.size=2"})
//@DisplayName("통합테스트, 댓글 삭제")
//@RequiredArgsConstructor
//class CommentDeleteApiTest extends IntegrationTest {
//
//    private final UserSetup userSetup;
//    private final BlogSetup blogSetup;
//    private final CommentSetup commentSetup;
//    private final CommentRepository commentRepository;
//
//    @DisplayName("댓글 삭제")
//    @Test
//    void testDeleteComment01() throws Exception {
//        //given
//        final User user = userSetup.saveOne();
//        TestUtil.setAuth(user);
//        final Blog blog = blogSetup.saveOne();
//        final Comment comment = commentSetup.saveOne(user, blog);
//
//        //when
//        mockMvc().perform(delete("/api/blogs/" + blog.getId() + "/comments/" + comment.getId()))
//                .andExpect(status().isOk());
//
//        //then
//        final List<Comment> comments = commentRepository.findAll();
//        assertThat(comments.isEmpty()).isTrue();
//    }
//
//    @DisplayName("댓글 삭제, 권한 없음")
//    @Test
//    void testDeleteComment02() throws Exception {
//        //given
//        final User authUser = userSetup.saveOne();
//        final User anotherUser = userSetup.saveOne();
//        TestUtil.setAuth(authUser);
//
//        final Blog blog = blogSetup.saveOne();
//        final Comment comment = commentSetup.saveOne(anotherUser, blog);
//
//        //when
//        mockMvc().perform(delete("/api/blogs/" + blog.getId() + "/comments/" + comment.getId()))
//                .andExpect(status().isUnauthorized());
//
//        //then
//        assertThat(commentRepository.findById(comment.getId())).isPresent();
//    }
//
//
//}