package com.podo.pododev.backend.job;

import com.podo.pododev.backend.global.context.ThreadLocalContext;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger("WORKER_LOGGER");

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
    private final List<Worker> workers;

    @Scheduled(cron = "0 */5 * * * *")
    public void doSchedule() {

        for (Worker worker : workers) {
            threadPoolExecutor.submit(() -> {
                ThreadLocalContext.init("job-"  + worker.getName());
                LocalDateTime now = LocalDateTime.now();
                try {
                    ThreadLocalContext.putDateTime("job.startAt", now);
                    worker.doWork(now);
                } catch (Exception e) {
                    Sentry.captureException(e);
                    ThreadLocalContext.putException(e);
                } finally {
                    ThreadLocalContext.putDateTime("job.endAt", LocalDateTime.now());
                    LOGGER.info("", StructuredArguments.value("context", ThreadLocalContext.toLog()));
                    ThreadLocalContext.clear();
                }
            });
        }
    }

    @PostConstruct
    public void workNow() {
        this.doSchedule();
    }

}
