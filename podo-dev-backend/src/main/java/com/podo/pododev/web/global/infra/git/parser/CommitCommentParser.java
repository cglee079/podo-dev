package com.podo.pododev.web.global.infra.git.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.git.GitEventType;
import com.podo.pododev.web.global.infra.git.GitResponseParser;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import org.kohsuke.github.GHCommitComment;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;

import java.io.IOException;
import java.net.URL;

public class CommitCommentParser implements GitResponseParser {
    @Override
    public GitEventVO parse(GHEventInfo event) throws IOException {
        final boolean aPrivate = event.getRepository().isPrivate();

        final GHEventPayload.CommitComment payload = event.getPayload(GHEventPayload.CommitComment.class);

        final GHCommitComment comment = payload.getComment();

        final URL htmlUrl = comment.getHtmlUrl();
        final String body = comment.getBody();

        return GitEventVO.builder()
                .eventType(GitEventType.COMMENT)
                .url(htmlUrl.toString())
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .contents(body)
                .build();
    }
}
