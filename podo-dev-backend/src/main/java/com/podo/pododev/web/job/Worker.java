package com.podo.pododev.web.job;

import java.time.LocalDateTime;

public interface Worker {
    void doWork(LocalDateTime now);
}
