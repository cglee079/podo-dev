package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.status.DefaultApiStatus;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogSetup;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.domain.user.User;
import com.podo.pododev.web.domain.user.UserSetup;
import com.podo.pododev.web.global.util.JsonUtil;
import com.podo.pododev.web.test.IntegrationTest;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
class CommentReadApiTest extends IntegrationTest {

    private final UserSetup userSetup;
    private final BlogSetup blogSetup;
    private final CommentSetup commentSetup;

    @Test
    void testGetRecentComments() throws Exception {

        final User user = userSetup.saveOne();
        final Blog blog = blogSetup.saveOne();
        final List<Comment> comments = commentSetup.save(user, blog, 1);

        mockMvc().perform(get("/api/comments/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(DefaultApiStatus.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(DefaultApiStatus.SUCCESS.getMessage()))
                .andExpect(jsonPath("$.result.size").value(1))
                .andExpect(jsonPath("$.result.contents[0].contents").value(comments.get(0).getContents()))
                .andExpect(jsonPath("$.result.contents[0].username").value(user.getUsername()));
    }
}