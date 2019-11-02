package com.cglee079.pododev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.TimeZone;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class PodoDevWebApplication {

    public PodoDevWebApplication() {
        //Telegram Api Initial
        ApiContextInitializer.init();

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PodoDevWebApplication.class, args);
    }

}
