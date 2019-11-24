package com.podo.pododev.web.global.infra.telegram;

import com.podo.pododev.web.global.infra.telegram.exception.TelegramException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

@Slf4j
@Component
public class TelegramClient extends TelegramLongPollingBot {

    @Value("${infra.telegram.bot.token}")
    private String botToken;

    @Value("${infra.telegram.bot.name}")
    private String botUsername;

    @Value("${infra.telegram.admin.telegram_id}")
    private String adminTelegramId;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //메세지 수신 시, 아무 로직도 수행하지 않음
    }

    public void send(String message) {

        final SendMessage sendMessage = new SendMessage(adminTelegramId, message);
        sendMessage.enableHtml(true);

        try {
            this.executeAsync(sendMessage, getDefaultCallback());
        } catch (TelegramApiException e) {
            log.error("관리자에게 알람을 전송 할 수 없습니다");
            throw new TelegramException(e);
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
                log.error("관리자에게 알람을 전송 할 수 없습니다");
                throw new TelegramException(e);
            }

            @Override
            public void onException(BotApiMethod<Message> method, Exception e) {
                log.error("관리자에게 알람을 전송 할 수 없습니다");
                throw new TelegramException(e);
            }
        };
    }
}
