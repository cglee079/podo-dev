package com.cglee079.pododev.web.job.maker;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MakeScheduler {

    private final List<MakeWorker> workers;
    private final BlogService blogService;


    @Scheduled(cron = "0 50 23 0/1 * *")
    public void doSchedule() {
        log.info("Start MakeWork Schedule");

        if (!blogService.existUpdated(LocalDate.now())) {
            log.info("No Updated Blogs");
            return;
        }

        log.info("Detect Updated Blog, Start MakeWork");

        List<BlogDto.summary> blogs = blogService.findEnabled();
        workers.forEach(w -> w.doWork(blogs));
    }


}
