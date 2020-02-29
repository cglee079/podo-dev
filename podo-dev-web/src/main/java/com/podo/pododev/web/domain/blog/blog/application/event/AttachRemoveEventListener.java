package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachUploader;
import com.podo.pododev.web.domain.blog.attach.AttachVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachRemoveEventListener {

    private final AttachUploader attachUploader;

    @EventListener
    public void consume(List<AttachVo> attaches){
        attachUploader.deleteToStorage(attaches);
    }
}
