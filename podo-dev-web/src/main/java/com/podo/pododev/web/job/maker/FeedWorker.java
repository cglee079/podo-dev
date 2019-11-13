package com.podo.pododev.web.job.maker;

import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.web.domain.blog.service.BlogFeedService;
import com.podo.pododev.web.job.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedWorker implements Worker {

    private final List<Worker> workers;
    private final BlogFeedService blogFeedService;

    @Override
    public void doWork() {

        if (!blogFeedService.hasYetNotFeed(false)) {
            log.info("No Updated Blogs");
            return;
        }

        log.info("Detect Updated Blog, Start MakeWork");

        List<BlogDto.feed> blogs = blogFeedService.findByEnabled();

        for (Worker work : workers) {
            work.doWork();
        }


        blogFeedService.completeFeed();

    }
}
