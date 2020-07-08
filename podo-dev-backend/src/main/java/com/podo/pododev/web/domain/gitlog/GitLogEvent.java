package com.podo.pododev.web.domain.gitlog;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.global.infra.git.GitEventType;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import lombok.Getter;

@Getter
public class GitLogEvent {

    private String createAt;
    private GitEventType eventType;
    private String url;
    private String contents;

    public GitLogEvent(GitEventVO gitEventVo) {
        this.eventType = gitEventVo.getEventType();
        this.url = gitEventVo.getUrl();
        this.contents = gitEventVo.getContents();
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(gitEventVo.getCreateAt());
    }
}
