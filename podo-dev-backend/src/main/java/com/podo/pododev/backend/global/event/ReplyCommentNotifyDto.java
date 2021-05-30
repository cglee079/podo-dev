package com.podo.pododev.backend.global.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReplyCommentNotifyDto {

    private Long blogId;
    private String username;
    private String email;
    private String contents;
    private String writer;
    private LocalDateTime writeAt;
    private String originContents;
    private String originWriter;
    private LocalDateTime originWriteAt;
}
