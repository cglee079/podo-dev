package com.podo.pododev.web.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("prod")
@Slf4j
@RequiredArgsConstructor
@Component
public class WorkScheduler {

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
    private final List<Worker> workers;

    @Scheduled(cron = "0 */5 * * * *")
    public void doSchedule() {
        for (Worker work : workers) {
            threadPoolExecutor.submit(() -> work.doWork(LocalDateTime.now()));
        }
    }

    @PostConstruct
    public void workNow(){
        this.doSchedule();
    }

}
