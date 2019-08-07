package com.cglee079.pododev.global.infra.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@RequiredArgsConstructor
@Configuration
public class TelegramConfig {

    private final TelegramClient telegramClient;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(telegramClient);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        return telegramBotsApi;

    }
}
