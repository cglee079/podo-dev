package com.podo.pododev.web.domain.blog.comment;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("블로그 댓글 테스트")
class CommentTest {

    @DisplayName("댓글 그룹 변경")
    @Test
    void testChangeCgroup(){
        //given
        Comment comment = createEmptyComment();
        final long cgroup = 100L;

        //when
        comment.changeCgroup(cgroup);

        //then
        assertThat(comment.getCgroup()).isEqualTo(cgroup);
    }

    @DisplayName("블로그 변경")
    @Test
    void testChangeBlog(){
        //given
        Comment comment = createEmptyComment();
        final Blog blog = Mockito.mock(Blog.class);

        //when
        comment.changeBlog(blog);

        //then
        assertThat(comment.getBlog()).isEqualTo(blog);
    }


    @DisplayName("자식 댓글 개수 증가")
    @Test
    void testIncreaseChildCount() {
        //given
        Comment comment = Comment.builder()
                .childCount(0)
                .build();

        //when
        comment.increaseChildCount();

        //then
        assertThat(comment.getChildCount()).isEqualTo(1);
    }

    @DisplayName("자식 댓글 개수 감소, 자식 댓글 없을 때")
    @Test
    void testDecreaseChildCount01() {
        //given
        Comment comment = Comment.builder()
                .childCount(0)
                .build();

        //when
        comment.decreaseChildCount();

        //then
        assertThat(comment.getChildCount()).isEqualTo(0);
    }

    @DisplayName("자식 댓글 개수 감소, 자식 댓글 있을 때")
    @Test
    void testDecreaseChildCount02() {
        //given
        Comment comment = Comment.builder()
                .childCount(100)
                .build();

        //when
        comment.decreaseChildCount();

        //then
        assertThat(comment.getChildCount()).isEqualTo(99);
    }

    @DisplayName("댓글 지우기, 삭제아님")
    @Test
    void testErase() {
        final String deletedContents = "삭제됨!!!";
        Comment comment = Comment.builder()
                .enabled(true)
                .build();

        //when
        comment.erase(deletedContents);

        //then
        assertThat(comment.getContents()).isEqualTo(deletedContents);
        assertThat(comment.getEnabled()).isFalse();
    }

    @DisplayName("삭제 될 수 있는 상태 체크")
    @Test
    void testCanDeleted() {
        Comment comment = Comment.builder()
                .childCount(0)
                .enabled(false)
                .build();

        //when, then
        assertThat(comment.canDeleted()).isTrue();
    }

    @DisplayName("최대 댓글 깊이 초과했을때")
    @ParameterizedTest
    @ValueSource(ints = {100, 101})
    void test(int depth){
        //given
        final int maxDepth = 100;
        Comment comment = Comment.builder()
                .depth(depth)
                .build();

        //when, then
        assertThat(comment.isExceedMaxCommentDepth(maxDepth)).isTrue();
    }

    @DisplayName("새로운 자식 댓글, Sort 값 생성")
    @ParameterizedTest(name = "댓글Depth : {0}, 자식댓글수 : {1}, Expect : {2}")
    @CsvSource({"0,0,2", "0,1,3", "1,0,1.001", "1,1,1.002", "1,2,1.003"})
    void testGetChildSort(Integer depth, Integer childCount, BigDecimal expected){
        Comment comment = Comment.builder()
                .sort(BigDecimal.ONE)
                .depth(depth)
                .childCount(childCount)
                .build();

        //when, then
        assertThat(comment.getChildCommentSort()).isEqualTo(expected);
    }

    @DisplayName("작성자 확인")
    @Test
    void testIsWrittenBy(){
        //given
        final long userId = 1L;
        final User user = Mockito.mock(User.class);
        given(user.getId()).willReturn(userId);

        final Comment comment = Comment.builder()
                .writer(user)
                .build();

        //when, then
        assertThat(comment.isWrittenBy(userId)).isTrue();
    }

    private Comment createEmptyComment() {
        return Comment.builder()
                .sort(BigDecimal.ONE)
                .childCount(0)
                .enabled(true)
                .build();
    }

}