package com.podo.pododev.backend.global.external.git.value;

import com.podo.pododev.backend.global.external.git.GitEventType;
import com.podo.pododev.backend.global.util.MarkdownUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class GitEventVO {

    private GitEventType eventType;
    private LocalDateTime createAt;
    private String url;
    private String contents;

    @Builder
    public GitEventVO(GitEventType eventType, LocalDateTime createAt, String url, String contents) {
        this.eventType = eventType;
        this.createAt = createAt;
        this.url = url;
        this.contents = MarkdownUtil.extractPlainText(contents.replace("\n",  " "));
    }
}
