package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachVO;
import lombok.Getter;

import java.util.List;

@Getter
public class DeleteFileOfAttachEvent {

    private List<AttachVO> attaches;

    public DeleteFileOfAttachEvent(List<AttachVO> attaches) {
        this.attaches = attaches;
    }
}
