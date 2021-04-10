package com.podo.pododev.web.job;

import java.time.LocalDateTime;

public interface Worker {

    String getName();
    void doWork(LocalDateTime now);
}
