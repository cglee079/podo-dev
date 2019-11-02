package com.cglee079.pododev.web.global.infra.telegram;

import com.cglee079.pododev.web.global.infra.telegram.exception.TelegramSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@RequiredArgsConstructor
@Configuration
public class TelegramConfig {

    private final TelegramClient telegramClient;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(telegramClient);

            return telegramBotsApi;
        } catch (TelegramApiRequestException e) {
          throw new TelegramSendException(e);
        }
    }
}
