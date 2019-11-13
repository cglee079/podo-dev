package com.cglee079.pododev.web.job.maker;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.service.BlogFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorkScheduler {

    private final List<Worker> workers;
    private final BlogFeedService blogFeedService;


    @Scheduled(cron = "0 */30 * * * *")
    public void doSchedule() {
        log.info("Start MakeWork Schedule");

        if (!blogFeedService.hasYetNotFeed(false)) {
            log.info("No Updated Blogs");
            return;
        }

        log.info("Detect Updated Blog, Start MakeWork");

        List<BlogDto.feed> blogs = blogFeedService.findByEnabled();


        for (Worker work : workers) {
            work.doWork(blogs);
        }


        blogFeedService.completeFeed();
    }


}
