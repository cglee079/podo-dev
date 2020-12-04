package com.podo.pododev.web.global.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCommentNotifyDto {

    private String blogTitle;
    private String username;
    private String content;
    private LocalDateTime writeAt;
}
