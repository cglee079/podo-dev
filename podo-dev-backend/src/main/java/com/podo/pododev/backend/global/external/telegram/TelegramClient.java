package com.podo.pododev.backend.global.external.telegram;

import com.podo.pododev.backend.global.external.telegram.exception.TelegramServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

@Slf4j
@Component
public class TelegramClient extends DefaultAbsSender {

    @Value("${external.telegram.bot.token:}")
    private String botToken;

    @Value("${external.telegram.admin.telegram_id:}")
    private String adminTelegramId;

    protected TelegramClient() {
        super(ApiContext.getInstance(DefaultBotOptions.class));
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void notifyAdmin(String message) {

        final SendMessage sendMessage = new SendMessage(adminTelegramId, message);
        sendMessage.enableHtml(true);

        try {
            this.executeAsync(sendMessage, getDefaultCallback());
        } catch (TelegramApiException e) {
            log.error("관리자에게 알람을 전송 할 수 없습니다 {}", e.getMessage(), e);
            throw new TelegramServerException(e);
        }

    }

    private SentCallback<Message> getDefaultCallback() {
        return new SentCallback<Message>() {
            @Override
            public void onResult(BotApiMethod<Message> method, Message response) {
                //No Logic
            }

            @Override
            public void onError(BotApiMethod<Message> method, TelegramApiRequestException e) {
                log.error("관리자에게 알람을 전송 할 수 없습니다 {}", e.getMessage(), e);
                throw new TelegramServerException(e);
            }

            @Override
            public void onException(BotApiMethod<Message> method, Exception e) {
                log.error("관리자에게 알람을 전송 할 수 없습니다 {}", e.getMessage(), e);
                throw new TelegramServerException(e);
            }
        };
    }
}
