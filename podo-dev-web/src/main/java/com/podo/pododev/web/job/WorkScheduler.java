package com.podo.pododev.web.job;

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


    @Scheduled(cron = "0 */30 * * * *")
    public void doSchedule() {
        for (Worker work : workers) {
            work.doWork();
        }
    }


}
