package com.podo.pododev.backend.global.external.git.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.backend.global.external.git.GitEventType;
import com.podo.pododev.backend.global.external.git.GitResponseParser;
import com.podo.pododev.backend.global.external.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequest;

import java.io.IOException;
import java.net.URL;

public class PullRequestParser implements GitResponseParser {

    @Override
    public GitEventVO parse(GHEventInfo event) throws IOException {
        final GHEventPayload.PullRequest payload = event.getPayload(GHEventPayload.PullRequest.class);
        final GHPullRequest pullRequest = payload.getPullRequest();

        final String contents = pullRequest.getBody();
        final String title = pullRequest.getTitle();
        final URL url = pullRequest.getHtmlUrl();

        return GitEventVO.builder()
                .eventType(GitEventType.PULL_REQUEST)
                .url(url.toString())
                .contents(title + " " + contents)
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .build();
    }
}
