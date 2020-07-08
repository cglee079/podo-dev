package com.podo.pododev.web.global.infra.git.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.git.GitEventType;
import com.podo.pododev.web.global.infra.git.GitResponseParser;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssue;

import java.io.IOException;
import java.net.URL;

public class IssueParser implements GitResponseParser {
    @Override
    public GitEventVO parse(GHEventInfo event) throws IOException {
        final GHEventPayload.Issue payload = event.getPayload(GHEventPayload.Issue.class);
        final GHIssue issue = payload.getIssue();

        final String action = payload.getAction();
        final String title = issue.getTitle();
        final String contents = issue.getBody();
        final URL url = issue.getHtmlUrl();

        return GitEventVO.builder()
                .eventType(GitEventType.ISSUE)
                .url(String.valueOf(url))
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(action + title + contents)
                .build();
    }
}
