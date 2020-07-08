package com.podo.pododev.web.global.infra.git.parser;

import com.podo.pododev.core.util.LocalDateTimeUtil;
import com.podo.pododev.web.global.infra.git.GitEventType;
import com.podo.pododev.web.global.infra.git.GitResponseParser;
import com.podo.pododev.web.global.infra.git.value.GitEventVO;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.List;

public class PushParser implements GitResponseParser {

    @Override
    public GitEventVO parse(GHEventInfo event) throws IOException {
        final GHRepository repository = event.getRepository();
        final GHEventPayload.Push payload = event.getPayload(GHEventPayload.Push.class);
        final List<GHEventPayload.Push.PushCommit> commits = payload.getCommits();

        if (commits.isEmpty()) {
            return null;
        }

        final String branch = payload.getRef().replace("refs/heads/", "");
        final String repositoryName = repository.getName();
        final String htmlUrl = repository.getHtmlUrl() + "/tree/" + branch;

        final String leastCommitMessage = commits.get(0).getMessage();
        final String contents = getContents(repositoryName, branch, leastCommitMessage, commits.size());

        return GitEventVO.builder()
                .eventType(GitEventType.PUSH)
                .url(htmlUrl)
                .contents(contents)
                .createAt(LocalDateTimeUtil.dateToLocalDateTime(event.getCreatedAt()))
                .build();
    }

    private String getContents(String repositoryName, String branch, String commitMessage, int commitSize) {
        if (commitSize > 1) {
            return String.format("%s/%s:: '%s' 외 %d 개", repositoryName, branch, commitMessage, commitSize - 1);
        }

        return String.format("%s/%s:: '%s'", repositoryName, branch, commitMessage);
    }

}
