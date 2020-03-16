package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachUploader;
import com.podo.pododev.web.domain.blog.attach.AttachVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteFileOfAttachEventListener {

    private final AttachUploader attachUploader;

    @TransactionalEventListener
    public void consume(DeleteFileOfAttachEvent event){
        attachUploader.deleteToStorage(event.getAttaches());
    }
}
