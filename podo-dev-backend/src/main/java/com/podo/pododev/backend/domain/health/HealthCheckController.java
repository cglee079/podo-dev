package com.podo.pododev.backend.domain.health;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {

    @Value("${spring.profiles.active:}")
    private final String phase;

    @GetMapping("/")
    public String home(){
        return phase;
    }

    @GetMapping("/trigger/exception")
    public String triggerException(){
        throw new RuntimeException("Trigger Exception");
    }
}
