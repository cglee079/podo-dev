package com.podo.pododev.web.global.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommentNotifyPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCommentNotify(UserCommentNotifyDto userCommentNotifyDto){
        applicationEventPublisher.publishEvent(userCommentNotifyDto);
    }
}