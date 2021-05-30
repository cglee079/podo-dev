package com.podo.pododev.backend.job.maker;

import com.podo.pododev.backend.domain.blog.blog.application.BlogFeedService;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogFeed;
import com.podo.pododev.backend.global.context.ThreadLocalContext;
import com.podo.pododev.backend.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedWorker implements Worker {

    private final List<FeedMakeExecutor> feedMakeExecutors;
    private final BlogFeedService blogFeedService;

    @Override
    public String getName() {
        return "feed-create";
    }

    @Override
    public void doWork(LocalDateTime now) {

        if (!blogFeedService.existByFeeded(false)) {
            ThreadLocalContext.debug("웹피드에 반영되지 않은 게시글이 없습니다");
            return;
        }

        ThreadLocalContext.debug("웹 피드에 반영되지 않은 게시글을 확인하였습니다. 웹피드 제작 작업을 수행합니다");

        final List<BlogFeed> blogs = blogFeedService.findByEnabledOrderByPublishDesc();

        for (FeedMakeExecutor feedMakeExecutor : feedMakeExecutors) {
            feedMakeExecutor.doExecute(blogs);
        }

        blogFeedService.completeFeed();
    }

}
