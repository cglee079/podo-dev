package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssue;

import java.io.IOException;
import java.net.URL;

public class IssueParser implements GitResponseParser {
    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final GHEventPayload.Issue payload = event.getPayload(GHEventPayload.Issue.class);
        final GHIssue issue = payload.getIssue();

        final String action = payload.getAction();
        final String title = issue.getTitle();
        final String contents = issue.getBody();
        final URL url = issue.getHtmlUrl();

        return GitEventVo.builder()
                .eventType(GitEventType.ISSUE)
                .url(String.valueOf(url))
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(action + title + contents)
                .build();
    }
}
