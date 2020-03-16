package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequest;

import java.io.IOException;
import java.net.URL;

public class PullRequestParser implements GitResponseParser {

    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final GHEventPayload.PullRequest payload = event.getPayload(GHEventPayload.PullRequest.class);
        final GHPullRequest pullRequest = payload.getPullRequest();

        final String contents = pullRequest.getBody();
        final String title = pullRequest.getTitle();
        final URL url = pullRequest.getHtmlUrl();

        return GitEventVo.builder()
                .eventType(GitEventType.PULL_REQUEST)
                .url(url.toString())
                .contents(title + " " + contents)
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .build();
    }
}
