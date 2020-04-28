package com.podo.pododev.web.domain.blog.tag.application;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.test.BlogSetup;
import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
@DisplayName("블로그 태그 조회, 통합 테스트")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class BlogTagReadServiceTest {

    private final BlogTagRepository blogTagRepository;
    private final BlogTagReadService blogTagReadService;
    private final BlogSetup blogSetup;

    @DisplayName("모든 태그 값, distinct 조회, ASC")
    @Test
    void testFindAllDistinctValue01(){
        //given
        final List<BlogTag> blogTags = Stream.of("A", "A", "B", "C")
                .map(BlogTag::new)
                .collect(toList());

        final Blog blog = blogSetup.saveOne();
        for (BlogTag blogTag : blogTags) {
            blogTag.changeBlog(blog);
        }

        blogTagRepository.saveAll(blogTags);

        //when
        final List<String> allDistinctTagValues = blogTagReadService.getAllDistinctTagValues(true);

        //then
        assertThat(allDistinctTagValues).containsExactly("A", "B", "C");
    }
}
