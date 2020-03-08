package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import org.kohsuke.github.GHCommitComment;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHIssueComment;

import java.io.IOException;
import java.net.URL;

public class CommitCommentParser implements GitResponseParser {
    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final boolean aPrivate = event.getRepository().isPrivate();

        final GHEventPayload.CommitComment payload = event.getPayload(GHEventPayload.CommitComment.class);

        final GHCommitComment comment = payload.getComment();

        final URL htmlUrl = comment.getHtmlUrl();
        final String body = comment.getBody();

        return GitEventVo.builder()
                .eventType(GitEventType.COMMENT)
                .url(htmlUrl.toString())
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(body)
                .build();
    }
}
