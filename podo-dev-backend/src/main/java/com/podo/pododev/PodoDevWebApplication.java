package com.podo.pododev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PodoDevWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PodoDevWebApplication.class, args);
    }

}
