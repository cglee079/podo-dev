package com.podo.pododev.web.global.infra.github.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.github.vo.GitEventVo;
import com.podo.pododev.web.global.infra.github.GitEventType;
import com.podo.pododev.web.global.infra.github.GitResponseParser;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PushParser implements GitResponseParser {

    @Override
    public GitEventVo parse(GHEventInfo event) throws IOException {
        final GHRepository repository = event.getRepository();
        final String repositoryName = repository.getName();
        final URL htmlUrl = repository.getHtmlUrl();
        final GHEventPayload.Push payload = event.getPayload(GHEventPayload.Push.class);
        final List<GHEventPayload.Push.PushCommit> commits = payload.getCommits();

        if (commits.isEmpty()) {
            return null;
        }

        final String leastCommitMessage = commits.get(0).getMessage();
        final String contents = getContents(repositoryName, leastCommitMessage, commits.size());

        return GitEventVo.builder()
                .eventType(GitEventType.PUSH)
                .url(String.valueOf(htmlUrl))
                .contents(contents)
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .build();
    }

    private String getContents(String repositoryName, String commitMessage, int commitSize) {
        if (commitSize > 1) {
            return String.format("%s:: '%s' 외 %d 개", repositoryName, commitMessage, commitSize - 1);
        }

        return String.format("%s:: '%s'", repositoryName, commitMessage);

    }

}
