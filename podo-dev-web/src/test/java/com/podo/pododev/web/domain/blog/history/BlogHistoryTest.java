package com.podo.pododev.web.domain.blog.history;

import com.podo.pododev.web.domain.blog.blog.Blog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("블로그 히스토리")
class BlogHistoryTest {


    @DisplayName("생성 By 블로그")
    @Test
    void testNewInstance(){
        //given
        final String title = "titles";
        final String contents = "contents";
        final Blog blog = Mockito.mock(Blog.class);
        given(blog.getTitle()).willReturn(title);
        given(blog.getContents()).willReturn(contents);

        //when
        final BlogHistory blogHistory = new BlogHistory(blog);

        //then
        assertThat(blogHistory.getBlog()).isEqualTo(blog);
        assertThat(blogHistory.getTitle()).isEqualTo(blog.getTitle());
        assertThat(blogHistory.getContents()).isEqualTo(blog.getContents());
    }

}