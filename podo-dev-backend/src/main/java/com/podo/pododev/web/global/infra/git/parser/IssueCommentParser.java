package com.podo.pododev.web.global.infra.git.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.git.GitEventType;
import com.podo.pododev.web.global.infra.git.GitResponseParser;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssueComment;

import java.io.IOException;
import java.net.URL;

public class IssueCommentParser implements GitResponseParser {
    @Override
    public GitEventVO parse(GHEventInfo event) throws IOException {
        final GHEventPayload.IssueComment payload = event.getPayload(GHEventPayload.IssueComment.class);
        final GHIssueComment comment = payload.getComment();

        final URL htmlUrl = comment.getHtmlUrl();
        final String action = payload.getAction();
        final String body = comment.getBody();

        return GitEventVO.builder()
                .eventType(GitEventType.COMMENT)
                .url(htmlUrl.toString())
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(body)
                .build();
    }
}
