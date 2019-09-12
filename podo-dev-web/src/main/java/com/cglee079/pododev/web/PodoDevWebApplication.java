package com.cglee079.pododev.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.cglee079.pododev")
@EnableJpaRepositories(basePackages = "com.cglee079.pododev")
@EntityScan(basePackages = "com.cglee079.pododev")
public class PodoDevWebApplication {

    public PodoDevWebApplication() {
        //Telegram Api Initial
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(PodoDevWebApplication.class, args);
    }

}
