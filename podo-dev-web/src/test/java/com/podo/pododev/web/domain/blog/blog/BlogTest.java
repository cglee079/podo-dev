package com.podo.pododev.web.domain.blog.blog;

import com.podo.pododev.web.domain.blog.attach.attachfile.AttachFile;
import com.podo.pododev.web.domain.blog.attach.attachimage.AttachImage;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.history.BlogHistory;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("블로그 테스트")
class BlogTest {

    @DisplayName("생성, 기본값 검증")
    @Test
    void testNewInstance() {
        //when
        final Blog blog = createEmptyBlog();

        //then
        assertThat(blog.getWebFeeded()).isFalse();
        assertThat(blog.getHitCount()).isZero();
        assertThat(blog.getAttachImages()).isEmpty();
        assertThat(blog.getAttachFiles()).isEmpty();
    }

    @DisplayName("title 변경")
    @Test
    void testChangeTitle() {
        //given
        final String title = "title";
        final Blog blog = createEmptyBlog();

        //when
        blog.changeTitle(title);

        //then
        assertThat(blog.getTitle()).isEqualTo(title);
        assertThat(blog.getWebFeeded()).isFalse();
    }

    @DisplayName("Content 변경")
    @Test
    void testChangeContent() {
        //given
        final String contents = "contents";
        final Blog blog = createEmptyBlog();

        //when
        blog.changeContents(contents);

        //then
        assertThat(blog.getContents()).isEqualTo(contents);
        assertThat(blog.getWebFeeded()).isFalse();
    }

    @DisplayName("HitCount 증가")
    @Test
    void testIncreaseHitCount() {
        //given
        final Blog blog = createEmptyBlog();

        //when
        blog.increaseHitCount();

        //then
        assertThat(blog.getHitCount()).isEqualTo(1);
    }

    @DisplayName("Tag 추가")
    @Test
    void testAddTag() {
        //given
        final BlogTag blogTag = Mockito.spy(BlogTag.class);
        final Blog blog = createEmptyBlog();

        //when
        blog.addTag(blogTag);

        assertThat(blog.getTags()).containsExactly(blogTag);
        assertThat(blogTag.getBlog()).isEqualTo(blog);
    }

    @DisplayName("Tag Clear")
    @Test
    void testClearTag() {
        //given
        final BlogTag blogTagA = Mockito.spy(BlogTag.class);
        final BlogTag blogTagB = Mockito.spy(BlogTag.class);
        final Blog blog = createEmptyBlog();

        blog.addTag(blogTagA);
        blog.addTag(blogTagB);

        //when
        blog.clearTags();

        //then
        assertThat(blog.getTags()).isEmpty();
        assertThat(blogTagA.getBlog()).isEqualTo(null);
        assertThat(blogTagB.getBlog()).isEqualTo(null);
    }

    @DisplayName("Comment 추가")
    @Test
    void testAddComment() {
        //given
        final Comment comment = Mockito.spy(Comment.class);
        final Blog blog = createEmptyBlog();

        //when
        blog.addComment(comment);

        //then
        assertThat(blog.getComments()).containsExactly(comment);
        assertThat(comment.getBlog()).isEqualTo(blog);
    }

    @DisplayName("AttachImage 추가")
    @Test
    void testAddAttachImage() {
        //given
        final AttachImage attachImage = Mockito.spy(AttachImage.class);
        final Blog blog = createEmptyBlog();

        //when
        blog.addAttachImage(attachImage);

        //then
        assertThat(blog.getAttachImages()).containsExactly(attachImage);
        assertThat(attachImage.getBlog()).isEqualTo(blog);
    }


    @DisplayName("AttachImage 삭제")
    @Test
    void testRemoveAttachImage() {
        //given
        final long attachImageId = 1L;
        final AttachImage attachImage = Mockito.spy(AttachImage.class);
        final Blog blog = createEmptyBlog();
        blog.addAttachImage(attachImage);
        given(attachImage.getId()).willReturn(attachImageId);

        //when
        blog.removeAttachImage(attachImageId);

        //then
        assertThat(blog.getAttachImages()).isEmpty();
        assertThat(attachImage.getBlog()).isNull();
    }

    @DisplayName("AttachFile 추가")
    @Test
    void testAddAttachFile() {
        //given
        final AttachFile attachFile = Mockito.spy(AttachFile.class);
        final Blog blog = createEmptyBlog();

        //given
        blog.addAttachFile(attachFile);

        //then
        assertThat(blog.getAttachFiles()).containsExactly(attachFile);
        assertThat(attachFile.getBlog()).isEqualTo(blog);
    }

    @DisplayName("AttachFile 삭제")
    @Test
    void testRemoveAttachFile() {
        //given
        final long attachFileId = 1L;
        final AttachFile attachFile = Mockito.spy(AttachFile.class);
        final Blog blog = createEmptyBlog();
        blog.addAttachFile(attachFile);

        given(attachFile.getId()).willReturn(attachFileId);

        //when
        blog.removeAttachFile(attachFileId);

        //then
        assertThat(blog.getAttachFiles()).isEmpty();
        assertThat(attachFile.getBlog()).isNull();
    }


    @DisplayName("BlogHistory 생성 Factory")
    @Test
    void testCreateHistory() {
        //given
        final Blog blog = createEmptyBlog();

        //when
        final BlogHistory history = blog.createHistory();

        //then
        assertThat(history).isNotNull();
        assertThat(blog.getHistories()).containsExactly(history);
    }

    @DisplayName("블로그 상태 변경 : Publish")
    @Test
    void testBlogUpdateStatus01() {
        final LocalDateTime dateTime = LocalDateTime.now();

        //given
        final Blog blog = createEmptyBlog();

        //when
        blog.updateStatus(BlogStatus.PUBLISH, dateTime);

        //then
        assertThat(blog.getPublishAt()).isEqualTo(dateTime);
        assertThat(blog.getEnabled()).isTrue();
    }

    @DisplayName("블로그 상태 변경 : VISIBLE")
    @Test
    void testBlogUpdateStatus02() {
        final LocalDateTime dateTime = LocalDateTime.now();

        //given
        final Blog blog = createEmptyBlog();

        //when
        blog.updateStatus(BlogStatus.VISIBLE, dateTime);

        //then
        assertThat(blog.getPublishAt()).isNotEqualTo(dateTime);
        assertThat(blog.getEnabled()).isTrue();
    }

    @DisplayName("블로그 상태 변경 : INVISIBLE")
    @Test
    void testBlogUpdateStatus03() {
        final LocalDateTime dateTime = LocalDateTime.now();

        //given
        final Blog blog = createEmptyBlog();

        //when
        blog.updateStatus(BlogStatus.INVISIBLE, dateTime);

        //then
        assertThat(blog.getPublishAt()).isNotEqualTo(dateTime);
        assertThat(blog.getEnabled()).isFalse();
    }

    private Blog createEmptyBlog() {
        return Blog.builder()
                .attachImages(Collections.emptyList())
                .attachFiles(Collections.emptyList())
                .hitCount(0)
                .webFeeded(false)
                .build();
    }
}