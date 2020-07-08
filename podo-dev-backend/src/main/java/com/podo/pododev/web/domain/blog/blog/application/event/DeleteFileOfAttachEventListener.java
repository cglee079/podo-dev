package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeleteFileOfAttachEventListener {

    private final AttachUploader attachUploader;

    @TransactionalEventListener
    public void consume(DeleteFileOfAttachEvent event){
        attachUploader.deleteToStorage(event.getAttaches());
    }
}
