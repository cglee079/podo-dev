package com.podo.pododev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.TimeZone;

@EnableScheduling
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
