package com.cglee079.pododev.web.global.infra.telegram;

import lombok.extern.java.Log;
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

@Log
@Component
public class TelegramClient extends TelegramLongPollingBot {

    @Value("${infra.telegram.podo.bot.token}")
    private String botToken;

    @Value("${infra.telegram.podo.user.name}")
    private String userName;

    @Value("${infra.telegram.podo.user.id}")
    private String userId;

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //No Logic..
    }


    public void send(String message){

        SendMessage sendMessage = new SendMessage(userId, message);
        sendMessage.enableHtml(true);

        try {
            this.executeAsync(sendMessage, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> method, Message response) {
                    //No Logic
                }

                @Override
                public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
                    apiException.printStackTrace();
                    log.info("관리자에게 알람을 전송 할 수 없습니다");
                }

                @Override
                public void onException(BotApiMethod<Message> method, Exception exception) {

                }
            });

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
