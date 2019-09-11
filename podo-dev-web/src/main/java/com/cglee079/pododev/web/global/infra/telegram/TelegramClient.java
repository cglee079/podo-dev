package com.cglee079.pododev.web.global.infra.telegram;

import com.cglee079.pododev.web.global.infra.telegram.exception.TelegramSendException;
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

    @Value("${infra.telegram.podo.bot.token}")
    private String botToken;

    @Value("${infra.telegram.podo.bot.name}")
    private String botUsername;

    @Value("${infra.telegram.podo.admin.id}")
    private String adminId;

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
        //No Logic..
    }

    public void send(String message) {

        final SendMessage sendMessage = new SendMessage(adminId, message);
        sendMessage.enableHtml(true);

        try {
            this.executeAsync(sendMessage, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> method, Message response) {
                    //No Logic
                }

                @Override
                public void onError(BotApiMethod<Message> method, TelegramApiRequestException e) {
                    log.error("관리자에게 알람을 전송 할 수 없습니다");
                    throw new TelegramSendException(e);
                }

                @Override
                public void onException(BotApiMethod<Message> method, Exception e) {
                    log.error("관리자에게 알람을 전송 할 수 없습니다");
                    throw new TelegramSendException(e);
                }
            });

        } catch (TelegramApiException e) {
            throw new TelegramSendException(e);
        }

    }
}
