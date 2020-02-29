package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachUploader;
import com.podo.pododev.web.domain.blog.attach.AttachVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachRemoveEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(List<AttachVo> attaches){
        eventPublisher.publishEvent(attaches);
    }
}
