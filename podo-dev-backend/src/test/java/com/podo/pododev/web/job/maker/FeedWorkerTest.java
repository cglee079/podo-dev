package com.podo.pododev.web.job.maker;

import com.podo.pododev.web.domain.blog.blog.application.BlogFeedService;
import com.podo.pododev.web.domain.blog.blog.dto.BlogFeed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("Feed 관련 JOB 단위테스트")
class FeedWorkerTest {

    @DisplayName("doWork, Feed 할 Blog가 없을 때")
    @Test
    void testDoWork01(){
        //given
        final FeedMakeExecutor feedMakeExecutorA = Mockito.mock(FeedMakeExecutor.class);
        final FeedMakeExecutor feedMakeExecutorB = Mockito.mock(FeedMakeExecutor.class);
        final BlogFeedService blogFeedService = Mockito.mock(BlogFeedService.class);

        given(blogFeedService.existByFeeded(false)).willReturn(false);

        final LocalDateTime now = LocalDateTime.now();

        final FeedWorker feedWorker = new FeedWorker(Arrays.asList(feedMakeExecutorA, feedMakeExecutorB), blogFeedService);

        //when
        feedWorker.doWork(now);

        //then
        then(feedMakeExecutorA).should(times(0)).doExecute(any());
        then(feedMakeExecutorB).should(times(0)).doExecute(any());
        then(blogFeedService).should(times(0)).completeFeed();
    }

    @DisplayName("doWork, Feed 할 Blog가 있을 때")
    @Test
    void testDoWork02(){
        //given
        final FeedMakeExecutor feedMakeExecutorA = Mockito.mock(FeedMakeExecutor.class);
        final FeedMakeExecutor feedMakeExecutorB = Mockito.mock(FeedMakeExecutor.class);
        final BlogFeedService blogFeedService = Mockito.mock(BlogFeedService.class);
        final List<BlogFeed> blogs = Mockito.any();

        given(blogFeedService.existByFeeded(false)).willReturn(true);
        given(blogFeedService.findByEnabledOrderByPublishDesc()).willReturn(blogs);

        final LocalDateTime now = LocalDateTime.now();

        final FeedWorker feedWorker = new FeedWorker(Arrays.asList(feedMakeExecutorA, feedMakeExecutorB), blogFeedService);

        //when
        feedWorker.doWork(now);

        //then
        then(feedMakeExecutorA).should(times(1)).doExecute(blogs);
        then(feedMakeExecutorB).should(times(1)).doExecute(blogs);
        then(blogFeedService).should(times(1)).completeFeed();
    }
}
