package com.podo.pododev.backend.job;

import java.time.LocalDateTime;

public interface Worker {

    String getName();
    void doWork(LocalDateTime now);
}
