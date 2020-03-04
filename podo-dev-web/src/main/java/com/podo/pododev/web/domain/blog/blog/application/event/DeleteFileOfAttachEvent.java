package com.podo.pododev.web.domain.blog.blog.application.event;

import com.podo.pododev.web.domain.blog.attach.AttachVo;
import lombok.Getter;

import java.util.List;

@Getter
public class DeleteFileOfAttachEvent {

    private List<AttachVo> attaches;

    public DeleteFileOfAttachEvent(List<AttachVo> attaches) {
        this.attaches = attaches;
    }
}
