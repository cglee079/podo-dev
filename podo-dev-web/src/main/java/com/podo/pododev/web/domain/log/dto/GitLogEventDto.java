package com.podo.pododev.web.domain.log.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import lombok.Getter;

@Getter
public class GitLogEventDto {

    private String createAt;
    private GitEventType eventType;
    private String url;
    private String contents;

    public GitLogEventDto(GitEventVo gitEventVo) {
        this.eventType = gitEventVo.getEventType();
        this.url = gitEventVo.getUrl();
        this.contents = gitEventVo.getContents();
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(gitEventVo.getCreateAt());
    }
}
