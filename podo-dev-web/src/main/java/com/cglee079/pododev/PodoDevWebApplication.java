package com.cglee079.pododev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PodoDevWebApplication {

    public PodoDevWebApplication() {
        //Telegram Api Initial
        ApiContextInitializer.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(PodoDevWebApplication.class, args);
    }

}
