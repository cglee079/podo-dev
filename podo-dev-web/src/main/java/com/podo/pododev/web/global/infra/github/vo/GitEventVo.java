package com.podo.pododev.web.global.infra.github.vo;

import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.util.MarkdownUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class GitEventVo {

    private GitEventType eventType;
    private LocalDateTime createAt;
    private String url;
    private String contents;

    @Builder
    public GitEventVo(GitEventType eventType, LocalDateTime createAt, String url, String contents) {
        this.eventType = eventType;
        this.createAt = createAt;
        this.url = url;
        this.contents = MarkdownUtil.extractPlainText(contents.replace("\n",  " "));
    }
}
