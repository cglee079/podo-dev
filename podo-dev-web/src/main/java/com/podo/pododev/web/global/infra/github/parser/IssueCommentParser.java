package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssueComment;

import java.io.IOException;
import java.net.URL;

public class IssueCommentParser implements GitResponseParser {
    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final GHEventPayload.IssueComment payload = event.getPayload(GHEventPayload.IssueComment.class);
        final GHIssueComment comment = payload.getComment();

        final URL htmlUrl = comment.getHtmlUrl();
        final String action = payload.getAction();
        final String body = comment.getBody();

        return GitEventVo.builder()
                .eventType(GitEventType.COMMENT)
                .url(htmlUrl.toString())
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(body)
                .build();
    }
}
