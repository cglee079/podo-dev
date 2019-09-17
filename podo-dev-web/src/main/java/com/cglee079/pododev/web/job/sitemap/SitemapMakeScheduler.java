package com.cglee079.pododev.web.job.sitemap;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SitemapMakeScheduler {

    private final SitemapWorker sitemapWorker;

    @Scheduled(cron = "0 50 23 0/1 * *")
    public void doSchedule() {
        sitemapWorker.work();
    }

}
