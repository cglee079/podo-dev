package com.cglee079.pododev.web.job.tempcleaner;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TempCleanerScheduler {

    private final TempCleaner tempCleaner;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void doSchedule() {
        tempCleaner.clean();
    }

}
