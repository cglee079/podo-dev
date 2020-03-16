package com.podo.pododev.web.global.infra.telegram;

import com.podo.pododev.web.global.infra.telegram.exception.TelegramException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@RequiredArgsConstructor
@Configuration
@Profile("deploy")
public class TelegramConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramClient telegramClient) {
        try {
            final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(telegramClient);

            return telegramBotsApi;
        } catch (TelegramApiRequestException e) {
          throw new TelegramException(e);
        }
    }
}
