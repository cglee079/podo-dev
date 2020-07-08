package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteFileOfAttachEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(List<AttachVO> attaches){
        eventPublisher.publishEvent(new DeleteFileOfAttachEvent(attaches));
    }
}
